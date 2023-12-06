package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interest")
data class Interest(
    @ColumnInfo(name = "name") val name: String,
) {
    @ColumnInfo(name = "interest_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
