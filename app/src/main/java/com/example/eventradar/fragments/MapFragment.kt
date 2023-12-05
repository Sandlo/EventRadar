package com.example.eventradar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eventradar.R
import com.example.eventradar.activities.MainActivity
import com.example.eventradar.helpers.OutOfScopeDialog
import com.google.android.material.search.SearchBar

class MapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val root = inflater.inflate(R.layout.fragment_map, container, false)

        root.findViewById<SearchBar>(R.id.search_bar).let {
            it.setOnClickListener {
                OutOfScopeDialog.show(requireContext())
            }
            it.setOnMenuItemClickListener {
                MainActivity.onAccountClicked(requireContext())
                true
            }
        }

        return root
    }
}
