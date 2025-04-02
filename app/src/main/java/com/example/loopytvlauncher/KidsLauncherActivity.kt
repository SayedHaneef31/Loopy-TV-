package com.example.loopytvlauncher


import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity

class KidsLauncherActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kids_launcher)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HomeFragment())
                .commit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            showPinDialog() // Show PIN dialog on home button press
            return true; // Prevent default home button behavior
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        showPinDialog() // Ensure PIN dialog appears immediately
    }

    public fun showPinDialog() {
        PinDialogFragment {
            // Prevent exiting without PIN
        }.show(supportFragmentManager, "PinDialog")
    }
}