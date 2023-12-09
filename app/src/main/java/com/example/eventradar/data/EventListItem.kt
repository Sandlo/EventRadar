package com.example.eventradar.data

import android.graphics.drawable.Drawable

/**
 * Data class representing an event list item with a rating, title, summary, and background drawable.
 *
 * @param rating The rating of the event.
 * @param title The title of the event.
 * @param summary The summary or description of the event.
 * @param background The background drawable associated with the event.
 */
data class EventListItem(
    val rating: Float,
    val title: String,
    val summary: String,
    val background: Drawable
)
