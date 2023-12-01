package com.example.eventradar.data.entities

import android.icu.text.SimpleDateFormat
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.eventradar.R
import com.example.eventradar.data.EventListItem
import java.util.Locale

@Entity
data class EventWithReviews(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "event_id"
    )
    val reviews: List<Review>
) {
    fun toListItem(): EventListItem {
        // TODO: Image
        return EventListItem(
            reviews.map { it.stars }.average().toFloat(),
            event.title,
            "${
                SimpleDateFormat(
                    "d. MMM yyyy",
                    Locale.getDefault()
                ).format(event.start)
            } • ${String.format("%.2f", event.price)} €",
            R.drawable.elena_de_soto
        )
    }
}