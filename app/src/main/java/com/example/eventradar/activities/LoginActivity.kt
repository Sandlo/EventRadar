package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.eventradar.R
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.helpers.Preferences
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

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
            val emailView = findViewById<TextInputLayout>(R.id.e_mail_phone_number)
            val passwordView = findViewById<TextInputLayout>(R.id.password)
            CoroutineScope(Dispatchers.Main).launch {
                val userAccount =
                    AppDatabase.getInstance(this@LoginActivity).accountDao()
                        .get(emailView.editText?.text.toString())
                if (userAccount == null ||
                    !BCrypt.checkpw(
                        passwordView.editText?.text.toString(), userAccount.passwordHash,
                    )
                ) {
                    emailView.error =
                        resources.getString(R.string.login_error)
                    passwordView.error = " "
                } else {
                    Preferences.setLoggedIn(this@LoginActivity, userAccount.id)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }
        }
    }
}
