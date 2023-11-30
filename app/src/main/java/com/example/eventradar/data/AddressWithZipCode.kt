package com.example.eventradar.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class AddressWithZipCode(
    @Embedded val address: Address,
    @Relation(
        parentColumn = "zip_code",
        entityColumn = "zip_code"
    )
    val zipCode: ZipCode
)