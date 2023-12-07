package com.example.eventradar.data.entities

import android.content.res.Resources
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.eventradar.R
import com.example.eventradar.data.SimpleListItem

@Entity
data class TicketWithEvent(
    @Embedded val ticket: Ticket,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "event_id",
    )
    val event: Event,
) {
    fun toListItem(resources: Resources): SimpleListItem {
        return SimpleListItem(
            event.title,
            event.getSummary(resources),
            R.drawable.ic_circle_tag,
        )
    }
}
