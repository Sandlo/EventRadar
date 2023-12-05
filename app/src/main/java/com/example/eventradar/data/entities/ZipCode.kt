package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zip_code")
data class ZipCode(
    @ColumnInfo(name = "zip_code") @PrimaryKey val zipCode: String,
    @ColumnInfo(name = "city") val city: String,
)
