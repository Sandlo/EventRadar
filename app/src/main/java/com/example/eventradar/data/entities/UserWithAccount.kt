package com.example.eventradar.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class UserWithAccount(
    @Embedded val user: User,
    @Relation(
        parentColumn = "account_id",
        entityColumn = "account_id",
    )
    val account: Account,
)
