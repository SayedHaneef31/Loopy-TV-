package com.example.loopytvlauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.leanback.widget.VerticalGridView
import com.example.loopytvlauncher.adapter.AppAdapter
import com.example.loopytvlauncher.data.AppInfo

class HomeFragment : Fragment() {
    private lateinit var appsGrid: VerticalGridView
    private val appsList = mutableListOf<AppInfo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appsGrid = view.findViewById(R.id.apps_grid)

        // Load approved apps
        loadApprovedApps()

        // Set up the grid adapter
        val adapter = AppAdapter(appsList) { appInfo ->
            launchApp(appInfo.packageName)
        }

        // Configure the grid
        appsGrid.adapter = adapter
        appsGrid.setNumColumns(4)  // Show 4 apps per row
        // We'll set up the grid adapter in the next step
    }

    private fun launchApp(packageName: String) {
        val launchIntent = requireActivity().packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            startActivity(launchIntent)
        }
    }

    private fun loadApprovedApps() {
        val pm = requireActivity().packageManager

        // Intent to find all launchable apps
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        // Get all launchable apps
        val allApps: List<ResolveInfo> = pm.queryIntentActivities(mainIntent, 0)

        // List of approved package names for kids
        val approvedPackages = listOf(
            "com.netflix.ninja",         // Netflix
            "com.disney.disneyplus",     // Disney+
            "com.google.android.youtube.tv", // YouTube
            "com.android.vending",       // Play Store (just for testing)
            // Add more approved apps as needed
        )

        // Filter apps based on approved packages
        for (info in allApps) {
            val packageName = info.activityInfo.packageName

            // Check if this app is approved
            if (packageName in approvedPackages) {
                val appInfo = AppInfo(
                    packageName = packageName,
                    appName = info.loadLabel(pm).toString(),
                    icon = info.loadIcon(pm)
                )
                appsList.add(appInfo)
            }
        }
    }
}