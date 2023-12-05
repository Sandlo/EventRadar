package com.example.eventradar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.eventradar.data.entities.Organizer

@Dao
interface OrganizerDao {
    @Insert
    suspend fun insertAll(vararg organizers: Organizer)
}
