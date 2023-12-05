package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.eventradar.R
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.helpers.Preferences
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.guest).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<Button>(R.id.register).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        findViewById<Button>(R.id.reset_password).setOnClickListener {
            OutOfScopeDialog.show(this)
        }

        findViewById<Button>(R.id.google_sign_in_button).setOnClickListener {
            OutOfScopeDialog.show(this)
        }

        findViewById<Button>(R.id.facebook_sign_in_button).setOnClickListener {
            OutOfScopeDialog.show(this)
        }

        findViewById<FloatingActionButton>(R.id.continue_button).setOnClickListener {
            Preferences.setLoggedIn(this, true)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
