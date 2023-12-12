package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.ErrorAdapter
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.data.entities.EventWithAddressOrganizerReviews
import com.example.eventradar.helpers.Base64
import com.example.eventradar.helpers.Preferences
import com.example.eventradar.helpers.StarView
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Aktivität für die Darstellung von Eventdetails und Interaktionsmöglichkeiten wie Teilen und Buchen.
 */
class EventActivity : BaseActivity(), RecyclerViewHelperInterface {
    companion object {
        /**
         * Konstante für den Schlüssel, der verwendet wird, um Event-Daten als Intent-Extra zwischen
         * Aktivitäten zu übertragen.
         */
        const val EVENT_INTENT_EXTRA: String = "event_intent_extra"
    }

    /**
     * Initialisiert die Eventaktivität und lädt Eventdetails und interaktive Funktionen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        findViewById<FloatingActionButton>(R.id.share).setOnClickListener {
            startActivity(
                Intent.createChooser(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
                        putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=dQw4w9WgXcQ")
                    },
                    resources.getString(R.string.share),
                ),
            )
        }

        findViewById<FloatingActionButton>(R.id.buy).setOnClickListener {
            onBuyClicked()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LoadingAdapter()

        if (!intent.hasExtra(EVENT_INTENT_EXTRA)) {
            recyclerView.adapter = ErrorAdapter()
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            val event =
                AppDatabase.getInstance(this@EventActivity).eventDao()
                    .getWithAddressOrganizerReviews(intent.getLongExtra(EVENT_INTENT_EXTRA, -1))

            if (event != null) {
                showEvent(
                    event,
                    findViewById(R.id.frame),
                    listOf(
                        findViewById(R.id.first_star),
                        findViewById(R.id.second_star),
                        findViewById(R.id.third_star),
                        findViewById(R.id.fourth_star),
                        findViewById(R.id.fifth_star),
                    ),
                    recyclerView,
                )
            } else {
                recyclerView.adapter = ErrorAdapter()
            }
        }
    }

    private fun onBuyClicked() {
        if (Preferences.isLoggedIn(this)) {
            startActivity(
                Intent(this, BookingActivity::class.java).putExtra(
                    EVENT_INTENT_EXTRA,
                    intent.getLongExtra(EVENT_INTENT_EXTRA, -1),
                ),
            )
        } else {
            MaterialAlertDialogBuilder(this).setTitle(R.string.title_login)
                .setMessage(R.string.booking_login_required)
                .setPositiveButton(R.string.title_login) { _, _ ->
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
        }
    }

    private fun showEvent(
        event: EventWithAddressOrganizerReviews,
        frame: View,
        stars: List<ImageView>,
        recyclerView: RecyclerView,
    ) {
        frame.background = Base64.decodeImage(this@EventActivity, event.event.image)
        StarView.fillStars(event.reviews.map { it.stars }.average().toFloat(), stars)
        frame.findViewById<TextView>(R.id.title).text = event.event.title
        frame.findViewById<TextView>(R.id.summary).text =
            event.event.getPriceAsLongString(resources)
        recyclerView.adapter =
            SimpleListAdapter(
                listOf(
                    SimpleListItem(
                        resources.getString(R.string.description),
                        event.event.description,
                        R.drawable.ic_circle_local_activity,
                    ),
                    SimpleListItem(
                        event.organizer.name,
                        resources.getString(R.string.organizer),
                        R.drawable.ic_circle_person,
                    ),
                    SimpleListItem(
                        event.event.getStartAsString(resources),
                        resources.getString(R.string.`when`),
                        R.drawable.ic_circle_calendar_today,
                    ),
                    SimpleListItem(
                        event.address.toString(resources),
                        resources.getString(R.string.where),
                        R.drawable.ic_circle_location_on,
                    ),
                ),
                this,
            )
    }

    /**
     * Reagiert auf Klickereignisse in der Eventliste, aktuell keine spezifische Implementierung.
     */
    override fun onItemClicked(position: Int) {
        // Do nothing.
    }
}
