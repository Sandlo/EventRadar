package com.example.eventradar.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "account_interest",
    primaryKeys = ["account_id", "interest_id"],
)
data class AccountInterest(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "interest_id") val interestId: Long,
)
