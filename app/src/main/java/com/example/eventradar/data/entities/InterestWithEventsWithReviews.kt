package com.example.eventradar.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.eventradar.data.CategoryListItem
import com.example.eventradar.interfaces.RecyclerViewHelperInterface

@Entity
data class InterestWithEventsWithReviews(
    @Embedded val interest: Interest,
    @Relation(
        parentColumn = "interest_id",
        entityColumn = "event_id",
        associateBy = Junction(EventInterest::class),
        entity = Event::class
    )
    val events: List<EventWithReviews>
) {
    fun toListItem(helperInterface: RecyclerViewHelperInterface): CategoryListItem {
        return CategoryListItem(interest.name, events.map { it.toListItem() }, helperInterface)
    }
}