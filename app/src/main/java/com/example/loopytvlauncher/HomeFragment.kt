package com.example.loopytvlauncher

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.registerReceiver
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appsGrid = view.findViewById(R.id.apps_grid)

        printInstalledApps(requireContext())
        // Load approved apps
        loadApprovedApps()

        // Set up the grid adapter
        val adapter = AppAdapter(appsList) { appInfo ->
            launchApp(appInfo.packageName)
        }

        // Configure the grid
        appsGrid.adapter = adapter
        appsGrid.setNumColumns(3)  // Show 4 apps per row
    }
    fun printInstalledApps(context: Context) {
        val pm = context.packageManager
        val installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        val systemApps = mutableListOf<String>()
        val thirdPartyApps = mutableListOf<String>()

        for (app in installedApps) {
            val isSystemApp = (app.flags and ApplicationInfo.FLAG_SYSTEM) != 0

            if (isSystemApp) {
                systemApps.add(app.packageName)
            } else {
                thirdPartyApps.add(app.packageName)
            }
        }

        // Print System Apps
        Log.d("SystemApps", "System Apps: ${systemApps.joinToString("\n")}")

        // Print Third-Party Apps
        Log.d("ThirdPartyApps", "Third-Party Apps: ${thirdPartyApps.joinToString("\n")}")
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
            "com.google.android.youtube.tvkids",         // Youtube kids

            "com.google.android.youtube.tv", // YouTube
            "com.android.vending",       // Play Store (just for testing)

        )

        // Filter apps based on approved packages
        for (info in allApps) {
            val packageName = info.activityInfo.packageName
            Log.d("HaneefDebug", "Found app: $packageName")

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

    private fun launchApp(packageName: String) {
        // Set the flag to indicate we're launching an app
        (requireActivity() as KidsLauncherActivity).setAppLaunching(true)
        
        val launchIntent = requireActivity().packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            startActivity(launchIntent)
        }
    }
}