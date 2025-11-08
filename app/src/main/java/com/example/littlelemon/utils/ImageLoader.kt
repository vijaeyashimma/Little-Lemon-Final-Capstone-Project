package com.example.littlelemon.utils

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * Loads an image from the assets folder and returns it as an ImageBitmap for Compose.
 */
fun loadImageFromAssets(context: Context, fileName: String): ImageBitmap? {
    return try {
        context.assets.open(fileName).use { input ->
            BitmapFactory.decodeStream(input)?.asImageBitmap()
        }
    } catch (_: Exception) {
        null
    }
}