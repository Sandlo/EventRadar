package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eventradar.data.entities.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    suspend fun getAll(): List<Event>

    @Query("SELECT * FROM event WHERE id = :id LIMIT 1")
    suspend fun get(id: Long): Event

    @Insert
    fun insertAll(vararg events: Event)
}