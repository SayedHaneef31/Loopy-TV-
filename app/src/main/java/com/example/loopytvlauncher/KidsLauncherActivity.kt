package com.example.loopytvlauncher


import android.os.Bundle
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

    // Override back button to prevent exiting
    override fun onBackPressed() {
        super.onBackPressed()
        // We'll add PIN protection here later
        // For now, just do nothing to prevent exit
    }
}