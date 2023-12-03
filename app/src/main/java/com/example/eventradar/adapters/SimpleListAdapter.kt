package com.example.eventradar.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.eventradar.R
import android.view.LayoutInflater
import com.example.eventradar.data.SimpleListItem
import com.example.eventradar.interfaces.RecyclerViewHelperInterface

class SimpleListAdapter(
    private val items: List<SimpleListItem>,
    private val helperInterface: RecyclerViewHelperInterface
) : RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_simple, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items[position].title.isEmpty()) {
            holder.title.visibility = View.GONE
        } else {
            holder.title.visibility = View.VISIBLE
            holder.title.text = items[position].title
        }
        if (items[position].summary.isEmpty()) {
            holder.summary.visibility = View.GONE
        } else {
            holder.summary.visibility = View.VISIBLE
            holder.summary.text = items[position].summary
        }
        holder.drawable.setImageResource(items[position].icon)
        holder.itemView.setOnClickListener { helperInterface.onItemClicked(position) }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val drawable: ImageView = view.findViewById(R.id.drawable)
        val title: TextView = view.findViewById(R.id.title)
        val summary: TextView = view.findViewById(R.id.summary)
    }
}