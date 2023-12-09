package com.example.eventradar.helpers

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.core.graphics.drawable.toDrawable
import java.io.InputStream

/**
 * Helper object for decoding Base64-encoded images and reading files from assets.
 */
object Base64 {
    /**
     * Decodes a Base64-encoded string into a Drawable.
     *
     * @param context The application context.
     * @param base64 The Base64-encoded string of the image.
     * @return A Drawable of the decoded image.
     */
    fun decodeImage(
        context: Context,
        base64: String,
    ): Drawable {
        val decodedString: ByteArray = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            .toDrawable(context.resources)
    }

    /**
     * Reads the content of a file from the assets directory and returns it as a Base64-encoded string.
     *
     * @param context The application context.
     * @param path The path to the file in the assets directory.
     * @return A Base64-encoded string of the file content.
     */
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
