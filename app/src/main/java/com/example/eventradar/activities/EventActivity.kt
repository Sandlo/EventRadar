package com.example.eventradar.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.helpers.Base64
import com.example.eventradar.helpers.StarView
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventActivity : BaseActivity(), RecyclerViewHelperInterface {

    companion object {
        const val EVENT_INTENT_EXTRA: String = "event_intent_extra"
    }

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
            startActivity(
                Intent(this, BookingActivity::class.java).putExtra(
                    EVENT_INTENT_EXTRA,
                    intent.getLongExtra(EVENT_INTENT_EXTRA, -1)
                )
            )
        }

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LoadingAdapter()

        if (!intent.hasExtra(EVENT_INTENT_EXTRA)) return
        CoroutineScope(Dispatchers.Main).launch {
            val event = AppDatabase.getInstance(this@EventActivity).eventDao()
                .getWithAddressOrganizerReviews(intent.getLongExtra(EVENT_INTENT_EXTRA, -1))

            if (event != null) {
                findViewById<View>(R.id.frame).background =
                    Base64.decodeImage(this@EventActivity, event.event.image)
                StarView.fillStars(
                    event.reviews.map { it.stars }.average().toFloat(),
                    listOf(
                        findViewById(R.id.first_star),
                        findViewById(R.id.second_star),
                        findViewById(R.id.third_star),
                        findViewById(R.id.fourth_star),
                        findViewById(R.id.fifth_star)
                    )
                )
                findViewById<TextView>(R.id.title).text = event.event.title
                findViewById<TextView>(R.id.summary).text =
                    event.event.getPriceAsString() + " inkl. MwSt."
                recyclerView.adapter = SimpleListAdapter(
                    listOf(
                        SimpleListItem(
                            resources.getString(R.string.description),
                            event.event.description,
                            R.drawable.ic_circle_local_activity
                        ),
                        SimpleListItem(
                            event.organizer.name,
                            resources.getString(R.string.organizer),
                            R.drawable.ic_circle_person
                        ),
                        SimpleListItem(
                            event.event.getStartAsString(),
                            resources.getString(R.string.`when`),
                            R.drawable.ic_circle_calendar_today
                        ),
                        SimpleListItem(
                            event.address.toString(),
                            resources.getString(R.string.where),
                            R.drawable.ic_circle_location_on
                        )
                    ),
                    this@EventActivity
                )
            }
        }
    }

    override fun onItemClicked(position: Int) {
        // Do nothing.
    }
}