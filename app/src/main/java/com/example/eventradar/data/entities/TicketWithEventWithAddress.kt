package com.example.eventradar.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class TicketWithEventWithAddress(
    @Embedded val ticket: Ticket,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "event_id",
        entity = Event::class,
    )
    val event: EventWithAddress,
)
