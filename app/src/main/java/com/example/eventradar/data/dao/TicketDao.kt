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
    @Query("SELECT * FROM ticket WHERE user_id = :userId")
    suspend fun getAll(userId: Long): List<TicketWithEvent>

    @Transaction
    @Query("SELECT * FROM ticket WHERE ticket_id = :id AND user_id =:userId LIMIT 1")
    suspend fun getWithEventWithAddress(
        id: Long,
        userId: Long,
    ): TicketWithEventWithAddress?

    @Insert
    suspend fun insertAll(vararg tickets: Ticket)

    @Insert
    suspend fun insert(ticket: Ticket): Long
}
