package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.eventradar.R

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.dummy_button).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        findViewById<Button>(R.id.second_dummy_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}