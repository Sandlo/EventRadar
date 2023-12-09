package com.example.eventradar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventradar.R
import com.example.eventradar.data.EventListItem
import com.example.eventradar.helpers.StarView
import com.example.eventradar.interfaces.RecyclerViewHelperInterface

/**
 * Adapter für eine RecyclerView, der Veranstaltungselemente anzeigt.
 */
class EventListAdapter(
    private val items: List<EventListItem>,
    private val helperInterface: RecyclerViewHelperInterface,
) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    /**
     * Erstellt einen neuen ViewHolder für Veranstaltungselemente.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_event, parent, false),
        )

    /**
     * Bindet Daten an einen ViewHolder, um ein Veranstaltungselement darzustellen.
     */
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.frame.background = items[position].background
        StarView.fillStars(items[position].rating, holder.stars)
        holder.title.text = items[position].title
        holder.summary.text = items[position].summary
        holder.itemView.setOnClickListener { helperInterface.onItemClicked(position) }
    }


    /**
     * Gibt die Anzahl der Veranstaltungselemente zurück.
     */
    override fun getItemCount(): Int = items.size

    /**
     * ViewHolder-Klasse für Veranstaltungselemente.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frame: RelativeLayout = view.findViewById(R.id.frame)
        val stars: List<ImageView> =
            listOf(
                view.findViewById(R.id.first_star),
                view.findViewById(R.id.second_star),
                view.findViewById(R.id.third_star),
                view.findViewById(R.id.fourth_star),
                view.findViewById(R.id.fifth_star),
            )
        val title: TextView = view.findViewById(R.id.title)
        val summary: TextView = view.findViewById(R.id.summary)
    }
}
