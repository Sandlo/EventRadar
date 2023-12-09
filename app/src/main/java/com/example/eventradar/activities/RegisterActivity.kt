package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import com.example.eventradar.R
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.entities.Account
import com.example.eventradar.data.entities.User
import com.example.eventradar.helpers.Preferences
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

/**
 * Aktivität für die Benutzerregistrierung mit Option zur Weiterleitung zur Interessenauswahl.
 */
class RegisterActivity : BaseActivity() {
    /**
     * Initialisiert die Registrierungsaktivität und setzt einen Event-Handler für den Fortfahren-Button.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            val forename = findViewById<TextInputLayout>(R.id.forename)
            val surname = findViewById<TextInputLayout>(R.id.surname)
            val birthdate = findViewById<TextInputLayout>(R.id.birthdate)
            val email = findViewById<TextInputLayout>(R.id.e_mail)
            val phoneNumber = findViewById<TextInputLayout>(R.id.phone_number)
            val password = findViewById<TextInputLayout>(R.id.password)
            val repeatPassword = findViewById<TextInputLayout>(R.id.repeat_password)

            CoroutineScope(Dispatchers.Main).launch {
                if (password.editText?.text.toString() != repeatPassword.editText?.text.toString()) {
                    password.error = resources.getString(R.string.login_error)
                } else {
                    val account =
                        AppDatabase.getInstance(this@RegisterActivity).accountDao()
                            .insert(
                                Account(
                                    email.editText?.text.toString(),
                                    phoneNumber.editText?.text.toString(),
                                    BCrypt.hashpw(password.editText?.text.toString(), BCrypt.gensalt()),
                                ),
                            )
                    AppDatabase.getInstance(this@RegisterActivity).userDao()
                        .insertAll(
                            User(
                                account,
                                forename.editText?.text.toString(),
                                surname.editText?.text.toString(),
                                birthdate.editText?.text.toString().toLong(),
                                // TODO: fix birthdate
                            ),
                        )
                    Preferences.setLoggedIn(this@RegisterActivity, account)
                    startActivity(Intent(this@RegisterActivity, InterestsActivity::class.java))
                }
            }
        }
    }
}
