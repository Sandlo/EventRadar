package com.example.eventradar.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class OrganizerWithAccount(
    @Embedded val organizer: Organizer,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id"
    )
    val account: Account
)