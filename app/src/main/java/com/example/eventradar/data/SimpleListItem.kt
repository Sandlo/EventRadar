package com.example.eventradar.data

/**
 * Data class representing a simple list item with a title, summary, and an icon.
 *
 * @param title The title of the list item.
 * @param summary The summary or description of the list item.
 * @param icon The resource ID of the icon associated with the list item.
 */
data class SimpleListItem(
    val title: String = "",
    val summary: String = "",
    val icon: Int = android.R.color.transparent
)
