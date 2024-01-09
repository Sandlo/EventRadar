package com.example.eventradar.helpers

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import com.example.eventradar.R
import com.example.eventradar.data.entities.AddressWithZipCode
import com.example.eventradar.data.entities.Event

object External {
    fun openCalendar(
        context: Context,
        event: Event,
    ) {
        try {
            context.startActivity(
                Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.Events.TITLE, event.title)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.start)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.end),
            )
        } catch (exception: PackageManager.NameNotFoundException) {
            Log.w(this::class.simpleName, exception)
            Toast.makeText(
                context,
                R.string.calendar_error,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    fun openMaps(
        context: Context,
        address: AddressWithZipCode,
    ) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address.toString(context.resources))}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        }
    }
}
