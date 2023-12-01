package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class Address(
    @ColumnInfo(name = "address_id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "street") val street: Long,
    @ColumnInfo(name = "zip_code") val zipCodeId: String,
    @ColumnInfo(name = "number") val number: Long
)