package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.eventradar.R

class EventActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        findViewById<Button>(R.id.dummy_button).setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java))
        }
    }
}