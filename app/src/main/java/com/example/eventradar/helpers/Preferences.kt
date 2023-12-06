package com.example.eventradar.helpers

import android.content.Context
import androidx.preference.PreferenceManager

object Preferences {
    private const val ACCOUNT_ID = "accountId"
    const val NO_ACCOUNT: Long = -1L

    fun isLoggedIn(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getLong(ACCOUNT_ID, NO_ACCOUNT) != NO_ACCOUNT
    }

    fun setLoggedIn(
        context: Context,
        accountId: Long,
    ) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putLong(ACCOUNT_ID, accountId)
            .apply()
    }

    fun getUserId(context: Context): Long {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getLong(ACCOUNT_ID, NO_ACCOUNT)
    }
}
