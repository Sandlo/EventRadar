package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.eventradar.R
import com.example.eventradar.helpers.Preferences

/**
 * Aktivität für die Auswahl und Verwaltung von Benutzerinteressen.
 */
class InterestsActivity : BaseActivity() {
    /**
     * Initialisiert die Interessenaktivität und setzt Event-Handler für Benutzerinteraktionen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        findViewById<Button>(R.id.dummy_button).setOnClickListener {
            Preferences.setLoggedIn(this, 1)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
