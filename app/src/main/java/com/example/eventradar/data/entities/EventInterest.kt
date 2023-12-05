package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "event_interest",
    primaryKeys = ["event_id", "interest_id"],
)
data class EventInterest(
    @ColumnInfo(name = "event_id") val eventId: Long,
    @ColumnInfo(name = "interest_id") val interestId: Long,
)
