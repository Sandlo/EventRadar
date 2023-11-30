package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eventradar.data.entities.Ticket

@Dao
interface TicketDao {
    @Query("SELECT * FROM ticket")
    suspend fun getAll(): List<Ticket>

    @Insert
    fun insertAll(vararg tickets: Ticket)
}