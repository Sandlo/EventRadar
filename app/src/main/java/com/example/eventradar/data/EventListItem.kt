package com.example.eventradar.data

import android.graphics.drawable.Drawable

data class EventListItem(
    val rating: Float,
    val title: String,
    val summary: String,
    val background: Drawable
)