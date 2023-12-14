package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.AccountInterest

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Account-Interessen-Daten in der Datenbank.
 */
@Dao
interface AccountInterestDao {
    /**
     * Fügt ein oder mehrere Interessen in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg interests: AccountInterest)
}
