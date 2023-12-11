package com.example.eventradar.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.ErrorAdapter
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.helpers.Preferences
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Aktivität zur Anzeige von Ticketinformationen und Interaktionsmöglichkeiten wie Stornierung.
 */
class TicketActivity : BaseActivity(), RecyclerViewHelperInterface {
    companion object {
        /**
         * Konstante für den Schlüssel, der verwendet wird, um Ticket-Daten als Intent-Extra
         * zwischen Aktivitäten zu übertragen.
         */
        const val TICKET_INTENT_EXTRA: String = "ticket_intent_extra"
        private const val CANCELLATION_ITEM = 4
    }

    /**
     * Initialisiert die Ticketaktivität und lädt Ticketdetails in eine Liste.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LoadingAdapter()

        if (!intent.hasExtra(TICKET_INTENT_EXTRA)) {
            recyclerView.adapter = ErrorAdapter()
            return
        }
        CoroutineScope(Dispatchers.Main).launch {
            val ticket =
                AppDatabase.getInstance(this@TicketActivity).ticketDao()
                    .getWithEventWithAddress(
                        intent.getLongExtra(TICKET_INTENT_EXTRA, -1),
                        Preferences.getUserId(this@TicketActivity),
                    )
            recyclerView.adapter =
                if (ticket != null) {
                    SimpleListAdapter(
                        listOf(
                            SimpleListItem("", resources.getString(R.string.ticket_info)),
                            SimpleListItem(
                                ticket.event.event.title,
                                resources.getString(R.string.what),
                                R.drawable.ic_circle_local_activity,
                            ),
                            SimpleListItem(
                                ticket.event.event.getStartAsString(resources),
                                resources.getString(R.string.`when`),
                                R.drawable.ic_circle_calendar_today,
                            ),
                            SimpleListItem(
                                ticket.event.address.toString(resources),
                                resources.getString(R.string.where),
                                R.drawable.ic_circle_location_on,
                            ),
                            SimpleListItem(resources.getString(R.string.ticket_cancel)),
                        ),
                        this@TicketActivity,
                    )
                } else {
                    ErrorAdapter()
                }
        }
    }

    /**
     * Reagiert auf Klickereignisse in der Ticketliste, insbesondere bei Auswahl der Stornierungsoption.
     */
    override fun onItemClicked(position: Int) {
        if (position == CANCELLATION_ITEM) OutOfScopeDialog.show(this)
    }
}
