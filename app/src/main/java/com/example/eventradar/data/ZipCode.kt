package com.example.eventradar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ZipCode(
    @PrimaryKey @ColumnInfo(name = "zip_code") val zipCode: String,
    @ColumnInfo(name = "city") val city: String
)