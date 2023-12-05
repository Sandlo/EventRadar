package com.example.eventradar.helpers

import android.widget.ImageView
import com.example.eventradar.R
import kotlin.math.min

object StarView {
    fun fillStars(
        rating: Float,
        stars: List<ImageView>,
    ) {
        val filledStars = min(rating.toInt(), stars.size)
        val hasHalfStar = rating % 1 >= 0.5
        for (star in stars) star.setImageResource(R.drawable.ic_small_star)
        for (star in stars.subList(0, filledStars)) star.setImageResource(
            R.drawable.ic_small_star_filled,
        )
        if (hasHalfStar && filledStars < stars.size) {
            stars[filledStars].setImageResource(
                R.drawable.ic_small_star_half,
            )
        }
    }
}
