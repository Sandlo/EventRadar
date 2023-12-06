package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @ColumnInfo(name = "e_mail") val eMail: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "password_hash") val passwordHash: String,
) {
    @ColumnInfo(name = "account_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
