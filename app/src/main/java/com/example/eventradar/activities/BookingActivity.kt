package com.example.eventradar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.helpers.Database
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
                SimpleListItem("Placeholder", "Titel das Events"),
                SimpleListItem("Placeholder","Preis inkl. MwSt."),
                SimpleListItem("Placeholder","Zahlungsmethode"),
                SimpleListItem("Placeholder","Eine ausführliche Rechnung und das Ticket bekommst du im Anschluss per Mail zugesandt. Das Ticket ist auch jeder Zeit in der App abrufbar.")
            ),
            this
        )



        findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            startActivity(Intent(this, TicketActivity::class.java))
        }
    }

    override fun onItemClicked(view: View, position: Int) {
        TODO("Not yet implemented")
    }
}