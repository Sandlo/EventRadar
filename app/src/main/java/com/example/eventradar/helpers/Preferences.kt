package com.example.eventradar.helpers

import android.content.Context
import androidx.preference.PreferenceManager

object Preferences {
    private const val IS_LOGGED_IN = "isLoggedIn"

    fun isLoggedIn(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(IS_LOGGED_IN, false)
    }

    fun setLoggedIn(
        context: Context,
        isLoggedIn: Boolean,
    ) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(IS_LOGGED_IN, isLoggedIn)
            .apply()
    }
}
