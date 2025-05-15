package nl.codingwithlinda.scribbledash.core.data.draw_examples.util

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import assertk.assert
import assertk.assertions.isEqualTo
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import org.junit.Test
import kotlin.math.roundToInt


class ParseVectorTest {

    val context = ApplicationProvider.getApplicationContext<Application>()
    val bmPrinter = AndroidBitmapPrinter(context)

    @Test
    fun testParseXMLDrawables(){
        val paths = resourceToDrawPaths(R.drawable.book, context)

        println("paths size: ${paths.size}")


        val coors = paths.map {
            pathToCoordinates(it, 5f)
        }
        coors.onEachIndexed { index, pathCoordinates ->
            pathCoordinates.chunked(10).onEach {chunk->
                println("path $index: ${chunk}")
            }
            val bm = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
            pathCoordinates.onEach {coor ->
                    bm.setPixel(coor.x.roundToInt(), coor.y.roundToInt(), Color.BLACK)
            }

            bmPrinter.printBitmap(bm, "test_parse_xml_$index.png")

        }
        assert(coors.size).isEqualTo(paths.size)

    }
}