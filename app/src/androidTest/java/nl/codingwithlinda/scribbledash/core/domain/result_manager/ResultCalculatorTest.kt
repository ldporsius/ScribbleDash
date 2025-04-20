package nl.codingwithlinda.scribbledash.core.domain.result_manager

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.flattenPaths
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.saveBitmapToFile
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.toBitmap
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawable
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawableMultiPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultCalculatorTest{

    private val calculator = ResultCalculator()
    private val context: Application = ApplicationProvider.getApplicationContext<Application>()
    private val provider = AndroidDrawExampleProvider.getInstance(context)



    @Test
    fun testFlattenPath(){
        val paths = testExampleDrawableMultiPath(context, R.drawable.umbrella)
        val flattenedPath = flattenPaths(paths.map { it.path })
        val fPath = SimpleDrawPath(
            path = flattenedPath,
        )

        val bmFlat = fPath.toBitmap(500, 2f)
        context.saveBitmapToFile(bmFlat, "test_flatten_path_flat.png")

        val drawResult = DrawResult(
            id = "",
            level = GameLevel.BEGINNER,
            examplePath = paths,
            userPath = paths
        )
        val accuracy = calculator.calculateResult(
            drawResult,
            strokeWidthUser = 1
        ){
            context.saveBitmapToFile(it, "test_flatten_path_result_umbrella.png")
        }

        assertEquals(100, accuracy)
    }
    @Test
    fun testCalculateResult_samePathAccuracy100(){
        val examplePath = testExampleDrawable(context, R.drawable.eye)
        val drawResult = DrawResult(
            id = "",
            level = GameLevel.BEGINNER,
            examplePath = listOf( examplePath),
            userPath = listOf(examplePath)
        )
        val accuracy = calculator.calculateResult(
            drawResult,
            strokeWidthUser = 1
        ){
            context.saveBitmapToFile(it, "test_calculate_result_eye.png")
        }

        assertEquals(100, accuracy)

    }

    @Test
    fun testCalculateResult_samePathDifferentStrokeWidthAccuracy100() {

        repeat(35) { i ->
            val  examplePath= provider.examples[i]

            val origBm = examplePath.toBitmap(500, 2f)
            context.saveBitmapToFile(origBm, "orig_bm_$i.png")

            val drawResult = DrawResult(
                id = "",
                level = GameLevel.BEGINNER,
                examplePath = listOf( examplePath),
                userPath = listOf(examplePath)
            )
            val accuracy = calculator.calculateResult(
                drawResult,
                1
            ) {
                context.saveBitmapToFile(it, "test_calculate_result_$i.png")
            }

            assertEquals(100, accuracy)

        }
    }
}