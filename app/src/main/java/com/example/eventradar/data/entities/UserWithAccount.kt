package com.example.eventradar.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

/**
 * Die Klasse UserWithAccount stellt eine Relation zwischen einem Benutzer (User) und seinem Konto (Account) in der Room-Datenbank dar.
 *
 * @property user Der eingebettete (embedded) Benutzer.
 * @property account Das Konto, das mit dem Benutzer verknüpft ist.
 */
@Entity
data class UserWithAccount(
    @Embedded val user: User,
    @Relation(
        parentColumn = "account_id",
        entityColumn = "account_id",
    )
    val account: Account,
)
