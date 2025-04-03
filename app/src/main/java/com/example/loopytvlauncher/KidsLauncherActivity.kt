package com.example.loopytvlauncher

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity
import android.app.ActivityManager
import android.content.Context

class KidsLauncherActivity : FragmentActivity() {
    private var isLauncherActive = false
    private var isAppLaunching = false
    private var isPinDialogShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kids_launcher)

        // Set this activity as the home screen
        if (intent?.categories?.contains(Intent.CATEGORY_HOME) == true) {
            isLauncherActive = true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HomeFragment())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        isLauncherActive = true
        isAppLaunching = false
        isPinDialogShowing = false
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        // Show PIN dialog when user tries to leave the app
        if (!isAppLaunching && !isPinDialogShowing) {
            showHomePinDialog()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // If we're launching an app, don't show PIN dialog
        if (isAppLaunching) {
            return super.onKeyDown(keyCode, event)
        }

        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                showPinDialog()
                return true
            }
            KeyEvent.KEYCODE_HOME -> {
                showHomePinDialog()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    // Method to be called when launching an app
    fun setAppLaunching(launching: Boolean) {
        isAppLaunching = launching
    }

    private fun showHomePinDialog() {
        if (!isPinDialogShowing) {
            isPinDialogShowing = true
            PinDialogFragment {
                // Launch Android home screen
                val homeIntent = Intent(Intent.ACTION_MAIN)
                homeIntent.addCategory(Intent.CATEGORY_HOME)
                homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(homeIntent)
                finish()
            }.show(supportFragmentManager, "HomePinDialog")
        }
    }

    private fun showPinDialog() {
        if (!isPinDialogShowing) {
            isPinDialogShowing = true
            PinDialogFragment {
                // Launch Android home screen
                val homeIntent = Intent(Intent.ACTION_MAIN)
                homeIntent.addCategory(Intent.CATEGORY_HOME)
                homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(homeIntent)
                finish()
            }.show(supportFragmentManager, "PinDialog")
        }
    }

    fun resetPinDialogShowing() {
        isPinDialogShowing = false
    }
}