package com.example.eventradar.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class EventWithAddressOrganizerReviews(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "address_id",
        entityColumn = "address_id",
        entity = Address::class,
    )
    val address: AddressWithZipCode,
    @Relation(
        parentColumn = "organizer_id",
        entityColumn = "organizer_id",
    )
    val organizer: Organizer,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "event_id",
    )
    val reviews: List<Review>,
)
