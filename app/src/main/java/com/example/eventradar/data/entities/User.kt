package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
open class User(
    @ColumnInfo(name = "user_id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "birthdate") val birthdate: Long
)