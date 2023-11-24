package com.example.eventradar.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.eventradar.R

class BookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        findViewById<Button>(R.id.dummy_button).setOnClickListener {
            startActivity(Intent(this, TicketActivity::class.java))
        }
    }
}