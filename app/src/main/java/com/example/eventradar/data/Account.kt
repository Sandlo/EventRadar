package com.example.eventradar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "e_mail") val eMail: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "password_hash") val passwordHash: String
)