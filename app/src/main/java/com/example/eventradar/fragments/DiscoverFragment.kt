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
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.entities.InterestWithEvents
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.search.SearchBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiscoverFragment : Fragment() {

    private var events: List<InterestWithEvents> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_discover, container, false)

        root.findViewById<SearchBar>(R.id.search_bar).let {
            it.setOnClickListener {
                OutOfScopeDialog.show(requireContext())
            }
            it.setOnMenuItemClickListener {
                MainActivity.onAccountClicked(requireContext())
                true
            }
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = LoadingAdapter()
        CoroutineScope(Dispatchers.Main).launch {
            events = AppDatabase.getInstance(requireContext()).interestDao().getAll()
            recyclerView.adapter = CategoryListAdapter(
                events.mapIndexed { index, event ->
                    event.toListItem(object : RecyclerViewHelperInterface {
                        override fun onItemClicked(view: View, position: Int) {
                            onItemClicked(index, position)
                        }
                    })
                }
            )
        }

        return root
    }

    internal fun onItemClicked(categoryPosition: Int, eventPosition: Int) {
        if (events.size > categoryPosition && events[categoryPosition].events.size > eventPosition)
            requireContext().startActivity(
                Intent(requireContext(), EventActivity::class.java).apply {
                    putExtra(
                        EventActivity.EVENT_INTENT_EXTRA,
                        events[categoryPosition].events[eventPosition].event.id
                    )
                }
            )
    }
}