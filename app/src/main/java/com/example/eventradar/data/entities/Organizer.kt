package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "organizer")
data class Organizer(
    @ColumnInfo(name = "name") val name: String,
) {
    @ColumnInfo(name = "organizer_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
