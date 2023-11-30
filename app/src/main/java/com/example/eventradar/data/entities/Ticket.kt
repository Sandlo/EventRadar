package com.example.eventradar.data.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.eventradar.R
import com.example.eventradar.data.AppDatabase
import com.example.eventradar.data.SimpleListItem

@Entity(tableName = "ticket")
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "event_id") val eventId: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "purchased_at") val purchasedAt: Long
) {
    private suspend fun getEvent(context: Context): Event {
        return AppDatabase.getInstance(context).eventDao().get(eventId)
    }

    suspend fun toListItem(context: Context): SimpleListItem {
        val event = getEvent(context)
        return SimpleListItem(
            event.title,
            event.start.toString() + " • " + event.price + " €",
            R.drawable.ic_circle_tag
        )
    }
}