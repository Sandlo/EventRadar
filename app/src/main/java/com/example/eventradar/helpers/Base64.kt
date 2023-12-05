package com.example.eventradar.helpers

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.core.graphics.drawable.toDrawable
import java.io.InputStream

object Base64 {
    fun decodeImage(
        context: Context,
        base64: String,
    ): Drawable {
        val decodedString: ByteArray = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            .toDrawable(context.resources)
    }

    fun getFromAssets(
        context: Context,
        path: String,
    ): String {
        val inputStream: InputStream = context.assets.open(path)
        val fileBytes = ByteArray(inputStream.available())
        inputStream.read(fileBytes)
        inputStream.close()
        return Base64.encodeToString(fileBytes, Base64.DEFAULT)
    }
}
