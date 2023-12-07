package com.example.eventradar.data.entities

import android.content.res.Resources
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.eventradar.R

@Entity
data class AddressWithZipCode(
    @Embedded val address: Address,
    @Relation(
        parentColumn = "zip_code",
        entityColumn = "zip_code",
    )
    val zipCode: ZipCode,
) {
    fun toString(resources: Resources): String =
        resources.getString(
            R.string.address_format,
            address.street,
            address.number,
            zipCode.zipCode,
            zipCode.city,
        )
}
