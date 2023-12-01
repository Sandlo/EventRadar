package com.example.eventradar.activities

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class TicketActivity : BaseActivity(), RecyclerViewHelperInterface {

    companion object {
        const val TICKET_INTENT_EXTRA: String = "ticket_intent_extra"
        private const val CANCELLATION_ITEM = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LoadingAdapter()

        if (!intent.hasExtra(TICKET_INTENT_EXTRA)) return
        CoroutineScope(Dispatchers.Main).launch {
            val ticket = AppDatabase.getInstance(this@TicketActivity).ticketDao()
                .get(intent.getLongExtra(TICKET_INTENT_EXTRA, -1))
            recyclerView.adapter = SimpleListAdapter(
                listOf(
                    SimpleListItem("", resources.getString(R.string.ticket_info)),
                    SimpleListItem(
                        ticket.event.event.title,
                        resources.getString(R.string.what),
                        R.drawable.ic_circle_local_activity
                    ),
                    SimpleListItem(
                        SimpleDateFormat(
                            "d. MMM yyyy 'um' H:mm 'Uhr'",
                            Locale.getDefault()
                        ).format(ticket.event.event.start),
                        resources.getString(R.string.`when`),
                        R.drawable.ic_circle_calendar_today
                    ),
                    SimpleListItem(
                        ticket.event.address.toString(),
                        resources.getString(R.string.where),
                        R.drawable.ic_circle_location_on
                    ),
                    SimpleListItem(resources.getString(R.string.ticket_cancel))
                ),
                this@TicketActivity
            )
        }
    }

    override fun onItemClicked(view: View, position: Int) {
        if (position == CANCELLATION_ITEM) OutOfScopeDialog.show(this)
    }


}