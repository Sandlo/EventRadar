package com.example.eventradar.data.entities

import android.icu.text.SimpleDateFormat
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.eventradar.R
import com.example.eventradar.data.SimpleListItem
import java.util.Locale

@Entity
data class TicketWithEvent(
    @Embedded val ticket: Ticket,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "event_id"
    )
    val event: Event
) {
    fun toListItem(): SimpleListItem {
        return SimpleListItem(
            event.title,
            "${
                SimpleDateFormat(
                    "d. MMM yyyy",
                    Locale.getDefault()
                ).format(event.start)
            } • ${String.format("%.2f", event.price)} €",
            R.drawable.ic_circle_tag
        )
    }
}