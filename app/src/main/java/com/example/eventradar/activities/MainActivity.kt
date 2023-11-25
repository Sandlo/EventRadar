package com.example.eventradar.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eventradar.R

class MainActivity : BaseActivity() {
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