package com.example.loopytvlauncher


import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity

class KidsLauncherActivity : FragmentActivity() {
    private var isLauncherActive = false
    private var isAppLaunching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kids_launcher)

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
    }

    override fun onPause() {
        super.onPause()
        isLauncherActive = false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // If we're launching an app, don't show PIN dialog
        if (isAppLaunching) {
            return super.onKeyDown(keyCode, event)
        }

        // If we're not in the launcher, let the current app handle the key
        if (!isLauncherActive) {
            return super.onKeyDown(keyCode, event)
        }

        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                showPinDialog()
                return true
            }
            KeyEvent.KEYCODE_HOME -> {
                showPinDialog()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        // Only show PIN dialog if we're in the launcher and not launching an app
        if (isLauncherActive && !isAppLaunching) {
            showPinDialog()
        }
    }

    // Method to be called when launching an app
    fun setAppLaunching(launching: Boolean) {
        isAppLaunching = launching
    }

    private fun showPinDialog() {
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