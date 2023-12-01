package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.ZipCode

@Dao
interface ZipCodeDao {
    @Insert
    suspend fun insertAll(vararg zipCodes: ZipCode)
}