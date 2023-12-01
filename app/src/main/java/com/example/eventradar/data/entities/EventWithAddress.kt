package com.example.eventradar.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class EventWithAddress(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "address_id",
        entityColumn = "address_id",
        entity = Address::class
    )
    val address: AddressWithZipCode
)