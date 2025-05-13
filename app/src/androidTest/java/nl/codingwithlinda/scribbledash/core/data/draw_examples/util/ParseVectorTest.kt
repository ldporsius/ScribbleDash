package nl.codingwithlinda.scribbledash.core.data.draw_examples.util

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import org.junit.Test
import kotlin.math.roundToInt

class ParseVectorTest {

    val context = ApplicationProvider.getApplicationContext<Application>()
    val bmPrinter = AndroidBitmapPrinter(context)

    @Test
    fun testParseXMLDrawables(){
        val coors = resourceToDrawPaths(R.drawable.alien, context).map {
            pathToCoordinates(it)
        }
        val bm = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        coors.onEach {list ->
            list.onEach {
                bm.setPixel(it.x.roundToInt(), it.y.roundToInt(), Color.BLACK)
            }
        }

        bmPrinter.printBitmap(bm, "test_parse_xml.png")
    }
}