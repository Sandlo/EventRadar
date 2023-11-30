package com.example.eventradar.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.interfaces.RecyclerViewHelperInterface

class TicketActivity : BaseActivity(), RecyclerViewHelperInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SimpleListAdapter(
            listOf(
                SimpleListItem("", resources.getString(R.string.ticket_info)),
                SimpleListItem(
                    "PLACEHOLDER",
                    resources.getString(R.string.what),
                    R.drawable.ic_circle_local_activity
                ),
                SimpleListItem(
                    "20. April 2045",
                    resources.getString(R.string.`when`),
                    R.drawable.ic_circle_calendar_today
                ),
                SimpleListItem(
                    "Bei Luca im Garten",
                    resources.getString(R.string.where),
                    R.drawable.ic_circle_location_on
                ),
                SimpleListItem(resources.getString(R.string.ticket_cancel))
            ),
            this
        )
    }

    override fun onItemClicked(view: View, position: Int) {
        // Do nothing.
    }


}