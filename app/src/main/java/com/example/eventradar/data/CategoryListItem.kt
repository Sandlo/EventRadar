package com.example.eventradar.data

data class CategoryListItem(
    val title: String = "",
    val list: List<SimpleListItem> = listOf()
)