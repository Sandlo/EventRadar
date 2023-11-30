package com.example.eventradar.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EventActivity : BaseActivity(), RecyclerViewHelperInterface {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        findViewById<FloatingActionButton>(R.id.share).setOnClickListener {
            startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
                putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=dQw4w9WgXcQ")
            }, resources.getString(R.string.share)))
        }

        findViewById<FloatingActionButton>(R.id.buy).setOnClickListener {
            startActivity(Intent(this, BookingActivity::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SimpleListAdapter(
            listOf(
                SimpleListItem(
                    resources.getString(R.string.description),
                    "PLACEHOLDER",
                    R.drawable.ic_circle_local_activity
                ),
                SimpleListItem(
                    "Luca",
                    resources.getString(R.string.organizer),
                    R.drawable.ic_circle_person
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
                )
            ),
            this
        )

        findViewById<View>(R.id.frame).setBackgroundResource(R.drawable.elena_de_soto)
        findViewById<TextView>(R.id.title).text = "PLACEHOLDER"
        findViewById<TextView>(R.id.summary).text = "PLACEHOLDER"
    }

    override fun onItemClicked(view: View, position: Int) {
        // Do nothing.
    }
}