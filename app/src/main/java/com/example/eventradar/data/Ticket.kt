package com.example.eventradar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "event_id") val eventId: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "purchased_at") val purchasedAt: Long
)