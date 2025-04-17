package nl.codingwithlinda.scribbledash.core.data.draw_examples.util

import android.app.Application
import androidx.compose.ui.graphics.Path
import androidx.test.core.app.ApplicationProvider
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import org.junit.Assert.assertEquals
import org.junit.Test

class BitmapUtilKtTest{

    val context = ApplicationProvider.getApplicationContext<Application>()
    val exampleProvider = AndroidDrawExampleProvider.getInstance(context)

    @Test
    fun testPathToBitmapConversion(){
        (0 until exampleProvider.examples.size).forEach{i ->
            val aPath = exampleProvider.examples[i]
            val bm = aPath.toBitmap(100)

            assertEquals(100, bm.width)
            assertEquals(100, bm.height)

            context.saveBitmapToFile(bm, "test_bitmap_$i.png")
        }

    }

    @Test
    fun testMultiplePathsToBitmapConversion(){
        val tPath = android.graphics.Path()
        val aPath =
            exampleProvider.examples[0]
        val bPath = exampleProvider.examples[0]

        bPath.path.offset(100f, 100f, tPath)

        val nPath = SimpleDrawPath(tPath)
        val bm = listOf(aPath, nPath).toBitmap(100, 8f)

        context.saveBitmapToFile(bm, "test_mutiple_paths_to_bitmap.png")
    }


}