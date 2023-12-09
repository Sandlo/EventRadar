package com.example.eventradar.data

import com.example.eventradar.interfaces.RecyclerViewHelperInterface

/**
 * Data class representing a category list item with a title, list of event items, and a helper interface.
 *
 * @param title The title of the category.
 * @param list The list of event items in the category.
 * @param helperInterface The RecyclerViewHelperInterface associated with the category.
 */
data class CategoryListItem(
    val title: String,
    val list: List<EventListItem>,
    val helperInterface: RecyclerViewHelperInterface
)
