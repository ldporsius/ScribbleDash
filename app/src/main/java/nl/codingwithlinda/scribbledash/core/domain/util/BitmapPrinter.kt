package nl.codingwithlinda.scribbledash.core.domain.util

import android.graphics.Bitmap

interface BitmapPrinter {

    fun printBitmap(bitmap: Bitmap, name: String)
}