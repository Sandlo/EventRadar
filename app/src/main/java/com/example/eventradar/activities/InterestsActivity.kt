package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.eventradar.R
import com.example.eventradar.helpers.Preferences

class InterestsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        findViewById<Button>(R.id.dummy_button).setOnClickListener {
            Preferences.setLoggedIn(this, 1)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
