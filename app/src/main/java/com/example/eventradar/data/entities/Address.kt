package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class Address(
    @ColumnInfo(name = "address_id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "street") val street: String,
    @ColumnInfo(name = "zip_code") val zipCode: String,
    @ColumnInfo(name = "number") val number: String
)