package com.example.eventradar.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.eventradar.R
import android.view.LayoutInflater

class ErrorAdapter : RecyclerView.Adapter<ErrorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_error, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Do nothing.
    }

    override fun getItemCount(): Int = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}