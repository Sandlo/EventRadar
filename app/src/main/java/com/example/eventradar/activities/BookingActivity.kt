package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.eventradar.R

class BookingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        findViewById<Button>(R.id.dummy_button).setOnClickListener {
            startActivity(Intent(this, TicketActivity::class.java))
        }
    }
}