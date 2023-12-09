package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertAll(vararg accounts: User)
}
