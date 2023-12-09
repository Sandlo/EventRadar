package com.example.eventradar.activities

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.InterestListAdapter
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Aktivität für die Auswahl und Verwaltung von Benutzerinteressen.
 */
class InterestsActivity : BaseActivity(), RecyclerViewHelperInterface {
    /**
     * Initialisiert die Interessenaktivität und setzt Event-Handler für Benutzerinteraktionen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        val recyclerView = findViewById<RecyclerView>(R.id.interest_list)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = LoadingAdapter()

        CoroutineScope(Dispatchers.Main).launch {
            val interest =
                AppDatabase.getInstance(this@InterestsActivity).interestDao()
                    .getAllInterests()
            val userInterests = null

            recyclerView.adapter =
                    InterestListAdapter(
                        interest,
                        this@InterestsActivity,
                    )

                /*
            if (userInterests.count >= 3) {
                userInterests.forEach { interest ->
                }
                val button = findViewById<ExtendedFloatingActionButton>(R.id.continue_interests)

            }
            else {
               /*
               Mindestens 3 Interessen auswählen
                */
            }

                 */
        }
    }

    override fun onItemClicked(position: Int) {

    }
}
