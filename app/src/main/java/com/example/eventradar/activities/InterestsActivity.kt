package com.example.eventradar.activities

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.InterestListAdapter
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.entities.AccountInterest
import com.example.eventradar.data.entities.Interest
import com.example.eventradar.helpers.Preferences
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Aktivität für die Auswahl und Verwaltung von Benutzerinteressen.
 */
class InterestsActivity : BaseActivity(), RecyclerViewHelperInterface {
    private var interest = listOf<Interest>()
    private var selectedInterest = mutableListOf<Interest>()

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
            interest =
                AppDatabase.getInstance(this@InterestsActivity).interestDao()
                    .getAllInterests()
            recyclerView.adapter =
                InterestListAdapter(
                    interest,
                    this@InterestsActivity,
                )
        }

        findViewById<FloatingActionButton>(R.id.continue_interests).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val accountInterestDao = AppDatabase.getInstance(this@InterestsActivity).accountInterestDao()
                val userId = Preferences.getUserId(this@InterestsActivity)

                val accountInterests =
                    selectedInterest.map { interest ->
                        AccountInterest(
                            userId,
                            interestId = interest.id,
                        )
                    }
                accountInterests.forEach { accountInterest ->
                    accountInterestDao.insertAll(accountInterest)
                }
            }
        }
    }

    override fun onItemClicked(position: Int) {
        if (selectedInterest.contains(interest[position])) {
            selectedInterest.remove(interest[position])
            return
        }
        selectedInterest.add(interest[position])
    }
}
