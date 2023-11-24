package com.example.eventradar.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.eventradar.R
import com.example.eventradar.activities.EventActivity
import com.example.eventradar.activities.TicketActivity

class TicketsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_tickets, null)

        root.findViewById<Button>(R.id.dummy_button).setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), TicketActivity::class.java))
        }

        return root
    }
}