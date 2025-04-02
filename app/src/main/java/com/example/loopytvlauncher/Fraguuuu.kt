//package com.example.loopytvlauncher
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.leanback.widget.VerticalGridView
//import com.example.loopytvlauncher.adapter.AppAdapter
//import com.example.loopytvlauncher.data.AppInfo
//import android.content.ComponentName
//import android.content.Context
//import android.content.pm.ApplicationInfo
//import android.content.pm.PackageManager
//import android.util.Log
//import android.widget.Toast
//
//class Fraguuuu : Fragment() {
//    private lateinit var appsGrid: VerticalGridView
//    private val appsList = mutableListOf<AppInfo>()
//    // Package names to try - note both YouTube TV and YouTube Kids TV
//    private val YOUTUBE_TV_PACKAGE = "com.google.android.youtube.tv"
//    private val YOUTUBE_KIDS_PACKAGE = "com.google.android.youtube.tvkids"
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_fraguuuu, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        appsGrid = view.findViewById(R.id.apps_grid)
//
//        // Debug: Print all installed packages first
////        printAllInstalledPackages()
//        printInstalledApps(requireContext())        // Then try to load YouTube apps
//        loadYouTubeApps()
//
//        // Configure the grid
//        appsGrid.setNumColumns(1)
//    }
//
//    fun printInstalledApps(context: Context) {
//        val pm = context.packageManager
//        val installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
//
//        val systemApps = mutableListOf<String>()
//        val thirdPartyApps = mutableListOf<String>()
//
//        for (app in installedApps) {
//            val isSystemApp = (app.flags and ApplicationInfo.FLAG_SYSTEM) != 0
//
//            if (isSystemApp) {
//                systemApps.add(app.packageName)
//            } else {
//                thirdPartyApps.add(app.packageName)
//            }
//        }
//
//        // Print System Apps
//        Log.d("SystemApps", "System Apps: ${systemApps.joinToString("\n")}")
//
//        // Print Third-Party Apps
//        Log.d("ThirdPartyApps", "Third-Party Apps: ${thirdPartyApps.joinToString("\n")}")
//    }
//    // Debug function to print all installed packages
//    private fun printAllInstalledPackages() {
//        try {
//            val pm = requireActivity().packageManager
//
//            // Get all packages with TV launcher intent
//            val tvIntent = Intent(Intent.ACTION_MAIN)
//            tvIntent.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
//            val tvApps = pm.queryIntentActivities(tvIntent, 0)
//
//            Log.d("AppDebug", "==== TV LAUNCHER APPS ====")
//            tvApps.forEach { resolveInfo ->
//                val packageName = resolveInfo.activityInfo.packageName
//                val appName = resolveInfo.loadLabel(pm).toString()
//                Log.d("AppDebug", "Found TV app: $appName ($packageName)")
//            }
//
//            // Get all installed packages
//            val installedPackages = pm.getInstalledPackages(0)
//            Log.d("AppDebug", "==== ALL INSTALLED PACKAGES ====")
//            Log.d("AppDebug", "Total installed packages: ${installedPackages.size}")
//
//            // Look specifically for YouTube-related packages
//            installedPackages.forEach { packageInfo ->
//                val packageName = packageInfo.packageName
//                if (packageName.contains("youtube", ignoreCase = true)) {
//                    try {
//                        val appName = packageInfo.applicationInfo?.let { pm.getApplicationLabel(it).toString() }
//                        Log.d("AppDebug", "Found YouTube app: $appName ($packageName)")
//                    } catch (e: Exception) {
//                        Log.d("AppDebug", "Found YouTube package but couldn't get label: $packageName")
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            Log.e("AppDebug", "Error listing packages: ${e.message}")
//        }
//    }
//
//    private fun launchApp(packageName: String) {
//        Log.d("AppLauncher", "Attempting to launch: $packageName")
//        try {
//            val pm = requireActivity().packageManager
//
//            // Try TV intent first
//            val tvIntent = Intent(Intent.ACTION_MAIN)
//            tvIntent.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
//            tvIntent.setPackage(packageName)
//
//            val resolveInfos = pm.queryIntentActivities(tvIntent, 0)
//
//            if (resolveInfos.isNotEmpty()) {
//                val resolveInfo = resolveInfos[0]
//                val componentName = ComponentName(
//                    resolveInfo.activityInfo.packageName,
//                    resolveInfo.activityInfo.name
//                )
//
//                val explicitIntent = Intent(Intent.ACTION_MAIN)
//                explicitIntent.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
//                explicitIntent.component = componentName
//                Log.d("AppLauncher", "Launching $packageName with TV intent")
//                startActivity(explicitIntent)
//            } else {
//                // Try regular intent
//                val launchIntent = pm.getLaunchIntentForPackage(packageName)
//
//                if (launchIntent != null) {
//                    Log.d("AppLauncher", "Launching $packageName with standard intent")
//                    startActivity(launchIntent)
//                } else {
//                    Log.e("AppLauncher", "Could not find any launch intent for $packageName")
//                    Toast.makeText(requireContext(), "Cannot launch app", Toast.LENGTH_LONG).show()
//                }
//            }
//        } catch (e: Exception) {
//            Log.e("AppLauncher", "Error launching $packageName: ${e.message}")
//            Toast.makeText(requireContext(), "Error launching app", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun loadYouTubeApps() {
//        val pm = requireActivity().packageManager
//        appsList.clear()
//
//        // Try to detect YouTube TV
//        tryLoadApp(YOUTUBE_TV_PACKAGE, "YouTube TV")
//
//        // Try to detect YouTube Kids TV
//        tryLoadApp(YOUTUBE_KIDS_PACKAGE, "YouTube Kids")
//
//        // Update the adapter with app list (whatever we found)
//        val adapter = AppAdapter(appsList) { appInfo ->
//            launchApp(appInfo.packageName)
//        }
//        appsGrid.adapter = adapter
//
//        if (appsList.isEmpty()) {
//            Log.d("AppLoading", "No YouTube apps found")
//            Toast.makeText(requireContext(), "No YouTube apps found", Toast.LENGTH_LONG).show()
//        } else {
//            Log.d("AppLoading", "Found ${appsList.size} YouTube apps")
//        }
//    }
//
//    private fun tryLoadApp(packageName: String, appLabel: String) {
//        try {
//            val pm = requireActivity().packageManager
//
//            // Check if package exists first
//            try {
//                val appInfo = pm.getApplicationInfo(packageName, 0)
//                val icon = pm.getApplicationIcon(appInfo)
//                val label = pm.getApplicationLabel(appInfo).toString()
//
//                // Package exists - add it to our list
//                appsList.add(AppInfo(
//                    packageName = packageName,
//                    appName = label,
//                    icon = icon
//                ))
//
//                Log.d("AppLoading", "Successfully loaded $appLabel ($packageName)")
//                return
//            } catch (e: PackageManager.NameNotFoundException) {
//                Log.d("AppLoading", "$appLabel package not found: $packageName")
//            }
//
//            // Alternative method - try with intents
//            val tvIntent = Intent(Intent.ACTION_MAIN)
//            tvIntent.addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
//            tvIntent.setPackage(packageName)
//
//            val tvResolveInfos = pm.queryIntentActivities(tvIntent, 0)
//
//            if (tvResolveInfos.isNotEmpty()) {
//                // Found the app with TV intent
//                val info = tvResolveInfos[0]
//                appsList.add(AppInfo(
//                    packageName = packageName,
//                    appName = info.loadLabel(pm).toString(),
//                    icon = info.loadIcon(pm)
//                ))
//                Log.d("AppLoading", "Found $appLabel with TV intent")
//                return
//            }
//
//            Log.d("AppLoading", "$appLabel not found with any method")
//
//        } catch (e: Exception) {
//            Log.e("AppLoading", "Error finding $appLabel: ${e.message}")
//        }
//    }
//}