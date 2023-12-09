package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.AccountInterest

@Dao
interface AccountInterestDao {
    @Insert
    suspend fun insertAll(vararg interests: AccountInterest)
}
