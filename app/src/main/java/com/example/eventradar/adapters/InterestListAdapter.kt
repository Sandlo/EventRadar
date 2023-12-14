package com.example.eventradar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.data.entities.Interest
import com.example.eventradar.helpers.Base64
import com.example.eventradar.interfaces.RecyclerViewHelperInterface

/**
 * Adapter für die Darstellung von Interessen in einer RecyclerView.
 */
class InterestListAdapter(
    private val items: List<Interest>,
    private val helperInterface: RecyclerViewHelperInterface,
) : RecyclerView.Adapter<InterestListAdapter.ViewHolder>() {
    private val states = Array(items.size) { _ -> false }

    /**
     * Erstellt eine neue ViewHolder-Instanz für die Darstellung eines Interesses.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_interest, parent, false),
        )

    /**
     * Bindet die Daten eines Interesses an eine ViewHolder-Instanz.
     */
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.gradient.background =
            ResourcesCompat.getDrawable(
                holder.gradient.resources,
                if (states[position]) R.drawable.gradient_primary else R.drawable.gradient_dim,
                holder.gradient.context.theme,
            )
        holder.title.text = items[position].name
        holder.image.setImageDrawable(Base64.decodeImage(holder.image.context, items[position].image))
        holder.itemView.setOnClickListener { helperInterface.onItemClicked(position) }
    }

    /**
     * Gibt die Anzahl der Interessen zurück.
     */
    override fun getItemCount(): Int = items.size

    /**
     * Gibt die IDs der ausgewählten Interessen zurück.
     */
    fun setSelected(
        position: Int,
        state: Boolean,
    ) {
        states[position] = state
        notifyItemChanged(position)
    }

    /**
     * ViewHolder für die Darstellung eines Interesses.
     * @property image ImageView für das Interessenbild.
     * @property gradient View für den Farbverlauf.
     * @property title TextView für den Interessennamen.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val gradient: View = view.findViewById(R.id.gradient)
        val title: TextView = view.findViewById(R.id.title)
    }
}
