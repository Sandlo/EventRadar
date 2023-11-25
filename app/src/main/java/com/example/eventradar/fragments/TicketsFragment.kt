package com.example.eventradar.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.activities.TicketActivity
import com.example.eventradar.adapters.SimpleListAdapter
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.interfaces.RecyclerViewHelperInterface

class TicketsFragment : Fragment(), RecyclerViewHelperInterface {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_tickets, container, false)

        val dummyItem = SimpleListItem(
            "Event",
            "1. April 2023 • 5,00 €",
            R.drawable.ic_circle_tag
        )

        val recyclerView = root.findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = SimpleListAdapter(
            Array(20) { dummyItem }.asList(),
            this
        )

        return root
    }

    override fun onItemClicked(view: View, position: Int) {
        Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
        requireContext().startActivity(Intent(requireContext(), TicketActivity::class.java))
    }
}