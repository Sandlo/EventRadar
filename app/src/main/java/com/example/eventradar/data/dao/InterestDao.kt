package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.eventradar.data.entities.Interest
import com.example.eventradar.data.entities.InterestWithEventsWithReviews

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Interessendaten in der Datenbank.
 */
@Dao
interface InterestDao {
    /**
     * Holt alle Interessen mit zugehörigen Veranstaltungen und Bewertungen aus der Datenbank.
     */
    @Transaction
    @Query("SELECT * FROM interest")
    suspend fun getAll(): List<InterestWithEventsWithReviews>

    /**
     * Holt alle Interessen aus der Datenbank.
     */
    @Query("SELECT * FROM interest")
    suspend fun getAllInterests(): List<Interest>

    /**
     * Fügt einen oder mehrere Interessensdatensätze in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg interests: Interest)
}
