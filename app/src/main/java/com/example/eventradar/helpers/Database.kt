package com.example.eventradar.helpers

import com.example.eventradar.R
import com.example.eventradar.data.EventListItem

object Database {

    fun getEvents(): List<EventListItem> {
        val dummyItem = EventListItem(
            3.5f,
            "Event",
            "1. April 2023 • 5,00 €",
            R.drawable.elena_de_soto
        )
        return Array(5) { dummyItem }.asList()
    }
}