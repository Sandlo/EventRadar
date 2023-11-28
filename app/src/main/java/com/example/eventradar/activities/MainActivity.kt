package com.example.eventradar.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eventradar.R
import com.example.eventradar.helpers.Preferences
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : BaseActivity() {

    companion object {
        fun onAccountClicked(context: Context) {
            if (Preferences.isLoggedIn(context)) {
                MaterialAlertDialogBuilder(context).setTitle(R.string.logout)
                    .setMessage(R.string.logout_summary)
                    .setPositiveButton(R.string.logout) { _, _ ->
                        Preferences.setLoggedIn(context, false)
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    }
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                    .show()
            } else {
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.nav_view).setupWithNavController(
            (supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment_activity_main
            ) as NavHostFragment).navController
        )
    }
}