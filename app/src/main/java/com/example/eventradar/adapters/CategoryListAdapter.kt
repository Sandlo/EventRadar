package com.example.eventradar.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.eventradar.R
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventradar.data.CategoryListItem

class CategoryListAdapter(
    private val items: List<CategoryListItem>
) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_category, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.list
        holder.list.layoutManager = LinearLayoutManager(
            holder.list.context,
            RecyclerView.HORIZONTAL,
            false
        )
        holder.list.adapter = EventListAdapter(
            items[position].list,
            items[position].helperInterface
        )
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val list: RecyclerView = view.findViewById(R.id.list)
    }
}