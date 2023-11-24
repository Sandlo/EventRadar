package com.example.eventradar.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eventradar.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = (supportFragmentManager.findFragmentById(
                    R.id.nav_host_fragment_activity_main
                ) as NavHostFragment).navController
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            ))
        )
        findViewById<BottomNavigationView>(R.id.nav_view).setupWithNavController(navController)
    }
}