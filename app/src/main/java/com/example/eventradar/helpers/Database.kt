package com.example.eventradar.helpers

import com.example.eventradar.R
import com.example.eventradar.data.EventListItem
import com.example.eventradar.data.SimpleListItem

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

    fun getTickets(): List<SimpleListItem> {
        val dummyItem = SimpleListItem(
            "Event",
            "1. April 2023 • 5,00 €",
            R.drawable.ic_circle_tag
        )
        return Array(5) { dummyItem }.asList()
    }
}