package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Die Klasse Interest repräsentiert ein Interesse in der Room-Datenbank.
 *
 * @property name Der Name des Interesses.
 */
@Entity(tableName = "interest")
data class Interest(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
) {
    /**
     * Die eindeutige ID des Interesses.
     */
    @ColumnInfo(name = "interest_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
