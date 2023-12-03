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
import com.example.eventradar.activities.MainActivity
import com.example.eventradar.activities.TicketActivity
import com.example.eventradar.adapters.EmptyAdapter
import com.example.eventradar.adapters.LoadingAdapter
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.entities.TicketWithEvent
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.chip.Chip
import com.google.android.material.search.SearchBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketsFragment : Fragment(), RecyclerViewHelperInterface {

    companion object {
        private const val DATE_FILTER: Byte = 0
        private const val TITLE_FILTER: Byte = 1
        private const val PRICE_FILTER: Byte = 2
    }

    private var tickets: List<TicketWithEvent> = listOf()
    private lateinit var dateFilter: Chip
    private lateinit var titleFilter: Chip
    private lateinit var priceFilter: Chip
    private lateinit var recyclerView: RecyclerView
    private var previousFilter: Byte = -1
    private var reversed = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_tickets, container, false)

        root.findViewById<SearchBar>(R.id.search_bar).let {
            it.setOnClickListener {
                OutOfScopeDialog.show(requireContext())
            }
            it.setOnMenuItemClickListener {
                MainActivity.onAccountClicked(requireContext())
                true
            }
        }

        dateFilter = root.findViewById(R.id.date_filter)
        titleFilter = root.findViewById(R.id.title_filter)
        priceFilter = root.findViewById(R.id.price_filter)

        recyclerView = root.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = LoadingAdapter()
        CoroutineScope(Dispatchers.Main).launch {
            tickets = AppDatabase.getInstance(requireContext()).ticketDao().getAll()
            if (tickets.isNotEmpty()) {
                dateFilter.setOnClickListener {
                    selectFilter(DATE_FILTER)
                }
                titleFilter.setOnClickListener {
                    selectFilter(TITLE_FILTER)
                }
                priceFilter.setOnClickListener {
                    selectFilter(PRICE_FILTER)
                }
                selectFilter(DATE_FILTER)
            } else {
                recyclerView.adapter = EmptyAdapter()
            }
        }

        return root
    }

    override fun onItemClicked(position: Int) {
        if (tickets.size > position) requireContext().startActivity(
            Intent(requireContext(), TicketActivity::class.java).apply {
                putExtra(TicketActivity.TICKET_INTENT_EXTRA, tickets[position].ticket.id)
            }
        )
    }

    private fun update(newTickets: List<TicketWithEvent>) {
        tickets = newTickets
        recyclerView.adapter = SimpleListAdapter(
            tickets.map { it.toListItem() },
            this@TicketsFragment
        )
    }

    private fun selectFilter(filter: Byte) {
        dateFilter.chipIcon = null
        titleFilter.chipIcon = null
        priceFilter.chipIcon = null
        reversed = if (previousFilter == filter) !reversed else false
        val icon = if (reversed) R.drawable.ic_keyboard_arrow_up
        else R.drawable.ic_keyboard_arrow_down
        when (filter) {
            DATE_FILTER -> {
                dateFilter.setChipIconResource(icon)
                update(
                    if (reversed) tickets.sortedBy { it.event.start }
                    else tickets.sortedByDescending { it.event.start }
                )
            }
            TITLE_FILTER -> {
                titleFilter.setChipIconResource(icon)
                update(
                    if (reversed) tickets.sortedBy { it.event.title }
                    else tickets.sortedByDescending { it.event.title }
                )
            }
            PRICE_FILTER -> {
                priceFilter.setChipIconResource(icon)
                update(
                    if (reversed)  tickets.sortedBy { it.event.price }
                    else tickets.sortedByDescending { it.event.price }
                )
            }
        }
        previousFilter = filter
    }
}