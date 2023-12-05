package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.Address

@Dao
interface AddressDao {
    @Insert
    suspend fun insertAll(vararg addresses: Address)
}
