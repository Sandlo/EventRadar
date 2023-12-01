package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class Event(
    @ColumnInfo(name = "event_id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "organizer_id") val organizerId: Long,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "start") val start: Long,
    @ColumnInfo(name = "end") val end: Long,
    @ColumnInfo(name = "address_id") val addressId: Long,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String
)