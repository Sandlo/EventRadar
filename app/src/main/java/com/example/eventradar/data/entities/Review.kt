package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class Review(
    @ColumnInfo(name = "event_id") val eventId: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "stars") val stars: Float,
    @ColumnInfo(name = "date") val date: Long,
) {
    @ColumnInfo(name = "review_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
