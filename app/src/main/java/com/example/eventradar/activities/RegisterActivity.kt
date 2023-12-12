package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
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
    private lateinit var forename: TextInputLayout
    private lateinit var surname: TextInputLayout
    private lateinit var birthdate: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var phoneNumber: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var repeatPassword: TextInputLayout

    /**
     * Initialisiert die Registrierungsaktivität und setzt einen Event-Handler für den Fortfahren-Button.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        forename = findViewById(R.id.forename)
        surname = findViewById(R.id.surname)
        birthdate = findViewById(R.id.birthdate)
        email = findViewById(R.id.e_mail)
        phoneNumber = findViewById(R.id.phone_number)
        password = findViewById(R.id.password)
        repeatPassword = findViewById(R.id.repeat_password)

        findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                listOf(
                    forename,
                    surname,
                    birthdate,
                    email,
                    phoneNumber,
                    password,
                    repeatPassword,
                ).forEach {
                    it.error = null
                }

                if (validate()) {
                    val account =
                        AppDatabase.getInstance(this@RegisterActivity).accountDao()
                            .insert(
                                Account(
                                    email.editText?.text.toString(),
                                    phoneNumber.editText?.text.toString(),
                                    BCrypt.hashpw(
                                        password.editText?.text.toString(),
                                        BCrypt.gensalt(),
                                    ),
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

    private fun validate(): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(forename.editText?.text.toString())) {
            isValid = false
            forename.error = resources.getString(R.string.forename_error)
        }
        if (TextUtils.isEmpty(surname.editText?.text.toString())) {
            isValid = false
            surname.error = resources.getString(R.string.surname_error)
        }
        if (TextUtils.isEmpty(birthdate.editText?.text.toString())) {
            isValid = false
            birthdate.error = resources.getString(R.string.birthdate_error)
        }
        if (TextUtils.isEmpty(email.editText?.text.toString()) ||
            !Patterns.EMAIL_ADDRESS.matcher(
                email.editText?.text.toString(),
            ).matches()
        ) {
            isValid = false
            email.error = resources.getString(R.string.email_error)
        }
        if (TextUtils.isEmpty(phoneNumber.editText?.text.toString()) ||
            !Patterns.PHONE.matcher(
                phoneNumber.editText?.text.toString(),
            ).matches()
        ) {
            isValid = false
            phoneNumber.error = resources.getString(R.string.phone_error)
        }
        if (TextUtils.isEmpty(password.editText?.text.toString())) {
            isValid = false
            password.error = resources.getString(R.string.password_empty_error)
        }
        if (password.editText?.text.toString() != repeatPassword.editText?.text.toString()) {
            isValid = false
            repeatPassword.error = resources.getString(R.string.passwords_do_not_match)
        }
        return isValid
    }
}
