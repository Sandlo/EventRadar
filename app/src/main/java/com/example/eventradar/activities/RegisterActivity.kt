package com.example.eventradar.activities

import android.content.Intent
import android.view.View
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.MotionEvent
import android.widget.ProgressBar
import com.example.eventradar.R
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.entities.Account
import com.example.eventradar.data.entities.User
import com.example.eventradar.helpers.Preferences
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import java.util.Calendar

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
    private var selectedDate: Long = 0

    val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

    private fun showDatePickerDialog() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build())
                .setTitleText(resources.getString(R.string.select_birthdate))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        birthdate.editText?.let { _ ->
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it
                selectedDate = it
                val selectedDate =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${calendar.get(
                        Calendar.YEAR,
                    )}"
                updateBirthdateEditText(selectedDate)
            }
        }

        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun updateBirthdateEditText(selectedDate: String) {
        val birthdateEditText = birthdate.editText
        birthdateEditText?.setText(selectedDate)
    }

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

        birthdate.editText?.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnTouchListener { view, motionEvent ->
                return@setOnTouchListener if (motionEvent.action == MotionEvent.ACTION_UP) {
                    showDatePickerDialog()
                    true
                } else {
                    view.performClick()
                }
            }
        }

        findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            progressBar.visibility = View.VISIBLE
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
                    Preferences.setLoggedIn(this@RegisterActivity, createUser())
                    startActivity(Intent(this@RegisterActivity, InterestsActivity::class.java))
                }
                progressBar.visibility = View.GONE
            }
        }
    }

    private suspend fun createUser(): Long {
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
                    selectedDate,
                ),
            )
        return account
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
