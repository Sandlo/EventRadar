package com.example.eventradar.data.entities

import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.eventradar.R
import java.util.Locale

@Entity(tableName = "event")
data class Event(
    @ColumnInfo(name = "organizer_id") val organizerId: Long,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "start") val start: Long,
    @ColumnInfo(name = "end") val end: Long,
    @ColumnInfo(name = "address_id") val addressId: Long,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String,
) {
    @ColumnInfo(name = "event_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    private fun getStartDate(): String =
        SimpleDateFormat(
            "d. MMM yyyy",
            Locale.getDefault(),
        ).format(start)

    private fun getStartTime(): String =
        SimpleDateFormat(
            "H:mm",
            Locale.getDefault(),
        ).format(start)

    fun getStartAsString(resources: Resources): String =
        resources.getString(
            R.string.start_format,
            getStartDate(),
            getStartTime(),
        )

    fun getPriceAsString(resources: Resources): String =
        resources.getString(R.string.currency_format, "%.2f".format(Locale.getDefault(), price))

    fun getPriceAsLongString(resources: Resources): String =
        resources.getString(
            R.string.vat_format,
            getPriceAsString(resources),
        )

    fun getSummary(resources: Resources): String =
        resources.getString(
            R.string.summary_format,
            getStartDate(),
            getPriceAsString(resources),
        )
}
