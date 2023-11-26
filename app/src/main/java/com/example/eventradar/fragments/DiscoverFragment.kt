package com.example.eventradar.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.eventradar.R
import com.example.eventradar.activities.EventActivity
import com.example.eventradar.activities.MainActivity
import com.example.eventradar.helpers.OutOfScopeDialog
import com.google.android.material.search.SearchBar

class DiscoverFragment : Fragment() {

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

        root.findViewById<CardView>(R.id.dummy_button).setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), EventActivity::class.java))
        }

        return root
    }
}