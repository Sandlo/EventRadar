package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.eventradar.data.entities.Ticket
import com.example.eventradar.data.entities.TicketWithEvent
import com.example.eventradar.data.entities.TicketWithEventWithAddress

@Dao
interface TicketDao {
    @Transaction
    @Query("SELECT * FROM ticket")
    suspend fun getAll(): List<TicketWithEvent>

    @Transaction
    @Query("SELECT * FROM ticket WHERE ticket_id = :id LIMIT 1")
    suspend fun get(id: Long): TicketWithEventWithAddress

    @Insert
    suspend fun insertAll(vararg tickets: Ticket)
}