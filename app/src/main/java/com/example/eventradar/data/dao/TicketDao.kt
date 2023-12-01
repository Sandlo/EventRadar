package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.eventradar.data.entities.Ticket
import com.example.eventradar.data.entities.TicketWithEvent

@Dao
interface TicketDao {
    @Transaction
    @Query("SELECT * FROM ticket")
    suspend fun getAll(): List<TicketWithEvent>

    @Insert
    fun insertAll(vararg tickets: Ticket)
}