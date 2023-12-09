package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.Address

/**
 * Data Access Object (DAO) Schnittstelle für den Zugriff auf Adressdaten in der Datenbank.
 */
@Dao
interface AddressDao {
    /**
     * Fügt einen oder mehrere Adressdatensätze in die Datenbank ein.
     */
    @Insert
    suspend fun insertAll(vararg addresses: Address)
}
