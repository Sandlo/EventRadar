package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.EventInterest

@Dao
interface EventInterestDao {
    @Insert
    fun insertAll(vararg interests: EventInterest)
}