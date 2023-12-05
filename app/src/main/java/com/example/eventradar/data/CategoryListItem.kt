package com.example.eventradar.data

import com.example.eventradar.interfaces.RecyclerViewHelperInterface

data class CategoryListItem(
    val title: String,
    val list: List<EventListItem>,
    val helperInterface: RecyclerViewHelperInterface,
)
