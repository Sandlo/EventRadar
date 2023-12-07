package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import com.example.eventradar.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            startActivity(Intent(this, InterestsActivity::class.java))
        }
    }
}
