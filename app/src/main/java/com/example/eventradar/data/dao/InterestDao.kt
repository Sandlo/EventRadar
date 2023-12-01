package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.eventradar.data.entities.Interest
import com.example.eventradar.data.entities.InterestWithEventsWithReviews

@Dao
interface InterestDao {
    @Transaction
    @Query("SELECT * FROM interest")
    suspend fun getAll(): List<InterestWithEventsWithReviews>

    @Insert
    suspend fun insertAll(vararg interests: Interest)
}