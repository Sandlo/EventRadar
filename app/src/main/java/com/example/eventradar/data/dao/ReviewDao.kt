package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.Review

@Dao
interface ReviewDao {
    @Insert
    suspend fun insertAll(vararg reviews: Review)
}