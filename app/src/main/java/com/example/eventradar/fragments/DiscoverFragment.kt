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
import com.example.eventradar.activities.EventActivity
import com.example.eventradar.activities.MainActivity
import com.example.eventradar.adapters.CategoryListAdapter
import com.example.eventradar.data.CategoryListItem
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.helpers.OutOfScopeDialog
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import com.google.android.material.search.SearchBar

class DiscoverFragment : Fragment(), RecyclerViewHelperInterface {

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

        val dummyItem = SimpleListItem(
            "Event",
            "1. April 2023 • 5,00 €",
            R.drawable.elena_de_soto
        )
        val dummyItems = Array(5) { dummyItem }.asList()

        val firstRecyclerView = root.findViewById<RecyclerView>(R.id.list)
        firstRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        firstRecyclerView.adapter = CategoryListAdapter(
            listOf(
                CategoryListItem(resources.getString(R.string.festivals), dummyItems),
                CategoryListItem(resources.getString(R.string.clubs), dummyItems),
                CategoryListItem(resources.getString(R.string.bars), dummyItems),
                CategoryListItem(resources.getString(R.string.food), dummyItems)
            ),
            this
        )

        return root
    }

    override fun onItemClicked(view: View, position: Int) {
        Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
        requireContext().startActivity(Intent(requireContext(), EventActivity::class.java))
    }
}