package com.example.eventradar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.data.entities.Interest
import com.example.eventradar.helpers.Base64
import com.example.eventradar.interfaces.RecyclerViewHelperInterface

class InterestListAdapter(
    private val items: List<Interest>,
    private val helperInterface: RecyclerViewHelperInterface,
) : RecyclerView.Adapter<InterestListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_interest, parent, false),
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.title.text = items[position].name
        holder.frame.background = (Base64.decodeImage(holder.frame.context, items[position].image))
        holder.itemView.setOnClickListener { helperInterface.onItemClicked(position) }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frame: RelativeLayout = view.findViewById(R.id.frame)
        val title: TextView = view.findViewById(R.id.title)
    }
}
