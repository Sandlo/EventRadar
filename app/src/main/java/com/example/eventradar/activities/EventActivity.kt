package com.example.eventradar.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Surface
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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
import com.example.eventradar.helpers.External
import com.example.eventradar.helpers.Preferences
import com.example.eventradar.helpers.StarView
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Aktivität für die Darstellung von Eventdetails und Interaktionsmöglichkeiten wie Teilen und Buchen.
 */
class EventActivity : BaseActivity(), RecyclerViewHelperInterface {
    private var event: EventWithAddressOrganizerReviews? = null

    /**
     * Initialisiert die Eventaktivität und lädt Eventdetails und interaktive Funktionen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        setupLayout()

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
            event =
                AppDatabase.getInstance(this@EventActivity).eventDao()
                    .getWithAddressOrganizerReviews(intent.getLongExtra(EVENT_INTENT_EXTRA, -1))

            if (event != null) {
                showEvent(
                    event ?: error(EVENT_IS_NULL),
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

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    private fun setupLayout() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )

        var resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val rotation = ContextCompat.getDisplayOrDefault(this).rotation
            val layoutParams = findViewById<View>(R.id.root).layoutParams as MarginLayoutParams

            when (rotation) {
                Surface.ROTATION_90 ->
                    layoutParams.setMargins(
                        0,
                        0,
                        resources.getDimensionPixelSize(resourceId),
                        0,
                    )
                Surface.ROTATION_270 ->
                    layoutParams.setMargins(
                        resources.getDimensionPixelSize(resourceId),
                        0,
                        0,
                        0,
                    )
                else ->
                    layoutParams.setMargins(
                        0,
                        0,
                        0,
                        resources.getDimensionPixelSize(resourceId),
                    )
            }
        }

        findViewById<MaterialToolbar>(R.id.top_app_bar).apply {
            resourceId =
                resources.getIdentifier(
                    "status_bar_height",
                    "dimen",
                    "android",
                )
            if (resourceId > 0) {
                (layoutParams as MarginLayoutParams).setMargins(
                    0,
                    resources.getDimensionPixelSize(resourceId),
                    0,
                    0,
                )
            }

            setNavigationOnClickListener {
                finish()
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
        frame.findViewById<ImageView>(R.id.image).setImageDrawable(
            Base64.decodeImage(this@EventActivity, event.event.image),
        )
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
     * Reagiert auf Klickereignisse in der Eventliste.
     * Öffnet Google Maps, wenn auf das Standortelement geklickt wird.
     */
    override fun onItemClicked(position: Int) {
        when (position) {
            LOCATION_ITEM -> External.openMaps(this, event?.address ?: error(EVENT_IS_NULL))
            DATE_ITEM -> External.openCalendar(this, event?.event ?: error(EVENT_IS_NULL))
        }
    }

    companion object {
        /**
         * Konstante für den Schlüssel, der verwendet wird, um Event-Daten als Intent-Extra zwischen
         * Aktivitäten zu übertragen.
         */
        const val EVENT_INTENT_EXTRA: String = "event_intent_extra"

        private const val LOCATION_ITEM = 3
        private const val DATE_ITEM = 2
        private const val EVENT_IS_NULL = "Event is null."
    }
}
