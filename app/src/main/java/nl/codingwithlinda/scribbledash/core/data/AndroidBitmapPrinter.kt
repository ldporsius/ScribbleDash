package nl.codingwithlinda.scribbledash.core.data

import android.content.Context
import android.graphics.Bitmap
import nl.codingwithlinda.scribbledash.core.data.util.saveBitmapToFile
import nl.codingwithlinda.scribbledash.core.domain.util.BitmapPrinter

class AndroidBitmapPrinter(
    private val context: Context
): BitmapPrinter {
    override fun printBitmap(bitmap: Bitmap, name: String) {
       context.saveBitmapToFile(bitmap, name )
    }
}