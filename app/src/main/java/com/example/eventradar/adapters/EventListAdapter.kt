package com.example.eventradar.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.eventradar.R
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.eventradar.data.EventListItem
import com.example.eventradar.interfaces.RecyclerViewHelperInterface
import kotlin.math.min

class EventListAdapter(
    private val items: List<EventListItem>,
    private val helperInterface: RecyclerViewHelperInterface
    ) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_event, parent, false)
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.frame.setBackgroundResource(items[position].background)
        fillStars(items[position].rating, holder.stars)
        holder.title.text = items[position].title
        holder.summary.text = items[position].summary
        holder.itemView.setOnClickListener { helperInterface.onItemClicked(holder.itemView, position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun fillStars(rating: Float, stars: List<ImageView>) {
        val filledStars = min(rating.toInt(), stars.size)
        val hasHalfStar  = rating % 1 >= 0.5
        for (star in stars) star.setImageResource(R.drawable.ic_small_star)
        for (star in stars.subList(0, filledStars)) star.setImageResource(
            R.drawable.ic_small_star_filled
        )
        if (hasHalfStar && filledStars < stars.size) stars[filledStars].setImageResource(
            R.drawable.ic_small_star_half
        )
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frame: RelativeLayout = view.findViewById(R.id.frame)
        val stars: List<ImageView> = listOf(
            view.findViewById(R.id.first_star),
            view.findViewById(R.id.second_star),
            view.findViewById(R.id.third_star),
            view.findViewById(R.id.fourth_star),
            view.findViewById(R.id.fifth_star)
        )
        val title: TextView = view.findViewById(R.id.title)
        val summary: TextView = view.findViewById(R.id.summary)
    }
}