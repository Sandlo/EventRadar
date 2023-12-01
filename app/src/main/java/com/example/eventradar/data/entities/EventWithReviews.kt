package com.example.eventradar.data.entities

import android.content.Context
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.eventradar.data.EventListItem
import com.example.eventradar.helpers.Base64

@Entity
data class EventWithReviews(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "event_id"
    )
    val reviews: List<Review>
) {
    fun toListItem(context: Context): EventListItem {
        return EventListItem(
            reviews.map { it.stars }.average().toFloat(),
            event.title,
            event.getSummary(),
            Base64.decodeImage(context, event.image)
        )
    }
}