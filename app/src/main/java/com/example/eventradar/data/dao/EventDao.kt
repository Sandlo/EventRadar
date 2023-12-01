package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.eventradar.data.entities.Event
import com.example.eventradar.data.entities.EventWithAddressOrganizerReviews

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM event WHERE event_id = :id LIMIT 1")
    suspend fun get(id: Long): EventWithAddressOrganizerReviews

    @Insert
    suspend fun insertAll(vararg events: Event)
}