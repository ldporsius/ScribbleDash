package nl.codingwithlinda.scribbledash.core.data.draw_examples.util

import android.content.Context
import android.graphics.Bitmap
import androidx.core.net.toUri
import java.io.File

fun Context.saveBitmapToFile(bitmap: Bitmap, filename: String) {
    val file = File(this.filesDir, filename)
    val uri = file.toUri()
    contentResolver.openOutputStream(uri)?.use {
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
}