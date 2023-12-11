package com.example.eventradar.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.activities.EventActivity
import com.example.eventradar.activities.MainActivity
import com.example.eventradar.adapters.CategoryListAdapter
import com.example.eventradar.adapters.EmptyAdapter
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.entities.InterestWithEventsWithReviews
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Fragment zur Entdeckung und Anzeige von Veranstaltungen basierend auf Benutzerinteressen.
 */
class DiscoverFragment : Fragment() {
    private var events: List<InterestWithEventsWithReviews> = listOf()

    /**
     * Erstellt die Ansicht für das Entdeckungsfragment und initialisiert Elemente wie Suchleiste und Eventliste.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val root = inflater.inflate(R.layout.fragment_discover, container, false)

        MainActivity.setupSearchBar(root)

        val recyclerView = root.findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = LoadingAdapter()
        CoroutineScope(Dispatchers.Main).launch {
            events = AppDatabase.getInstance(requireContext()).interestDao().getAll()
            recyclerView.adapter =
                if (events.isNotEmpty()) {
                    CategoryListAdapter(
                        events.mapIndexed { index, event ->
                            event.toListItem(
                                requireContext(),
                                object : RecyclerViewHelperInterface {
                                    override fun onItemClicked(position: Int) {
                                        onItemClicked(index, position)
                                    }
                                },
                            )
                        },
                    )
                } else {
                    EmptyAdapter()
                }
        }

        return root
    }

    /**
     * Behandelt Klickereignisse auf Veranstaltungen und leitet zum entsprechenden EventActivity weiter.
     */
    internal fun onItemClicked(
        categoryPosition: Int,
        eventPosition: Int,
    ) {
        if (events.size > categoryPosition && events[categoryPosition].events.size > eventPosition) {
            requireContext().startActivity(
                Intent(requireContext(), EventActivity::class.java).apply {
                    putExtra(
                        EventActivity.EVENT_INTENT_EXTRA,
                        events[categoryPosition].events[eventPosition].event.id,
                    )
                },
            )
        }
    }
}
