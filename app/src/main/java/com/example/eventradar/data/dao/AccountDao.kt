package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eventradar.data.entities.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account WHERE e_mail = :eMailOrPhone OR phone = :eMailOrPhone LIMIT 1")
    suspend fun get(eMailOrPhone: String): Account?

    @Insert
    suspend fun insertAll(vararg accounts: Account)
}
