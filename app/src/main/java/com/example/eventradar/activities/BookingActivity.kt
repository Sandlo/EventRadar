package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BookingActivity : BaseActivity(), RecyclerViewHelperInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SimpleListAdapter(
            listOf(
                SimpleListItem("Placeholder", resources.getString(R.string.booking_title)),
                SimpleListItem("Placeholder", resources.getString(R.string.booking_price)),
                SimpleListItem("Placeholder", resources.getString(R.string.booking_payment)),
                SimpleListItem("",resources.getString(R.string.booking_info))
            ),
            this
        )

        findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            startActivity(Intent(this, TicketActivity::class.java))
        }
    }

    override fun onItemClicked(view: View, position: Int) {
        // Do nothing.
    }
}