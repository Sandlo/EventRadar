package com.example.eventradar.activities

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.eventradar.R
import com.example.eventradar.data.AppDatabase
import com.google.android.material.elevation.SurfaceColors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (resources.configuration.uiMode.and(
                Configuration.UI_MODE_NIGHT_MASK
        ) != Configuration.UI_MODE_NIGHT_YES) setTheme(R.style.LightStatusBarOverlay)
        val color = SurfaceColors.SURFACE_2.getColor(this)
        window.statusBarColor = color
        window.navigationBarColor = color

        GlobalScope.launch {
            Log.wtf(
                "App",
                AppDatabase.getInstance(this@BaseActivity).ticketDao().getAll().toString()
            )
            Log.wtf(
                "App",
                AppDatabase.getInstance(this@BaseActivity).eventDao().getAll().toString()
            )
        }
    }
}