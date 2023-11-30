package com.example.eventradar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "street") val street: Long,
    @ColumnInfo(name = "zip_code") val zipCodeId: String,
    @ColumnInfo(name = "number") val number: Long
)