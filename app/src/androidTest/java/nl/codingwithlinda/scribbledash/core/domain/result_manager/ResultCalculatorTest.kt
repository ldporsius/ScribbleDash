package nl.codingwithlinda.scribbledash.core.domain.result_manager

import android.app.Application
import android.graphics.Path
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

    private fun getPathLength(path: Path): Float
    {
        val pathMeasure = android.graphics.PathMeasure(path, true)
        return pathMeasure.length

    }
    @Test
    fun testHalfLenghtPath_pathLengthPenaltyIsApplied(){
        val userPath = provider.getByResId(R.drawable.eye)

        val examplePath = List(2){
            userPath
        }

        examplePath.onEach {
            println("-- in test half length path --. examplePath.size = ${getPathLength(it.path)}")

        }
        examplePath.fold(0f) { acc, androidDrawPath ->
            getPathLength(androidDrawPath.path) + acc
        }.also {
            println("-- in test half length path --. total length = $it")

        }
        val drawResult = DrawResult(
            id = "",
            level = GameLevel.BEGINNER,
            examplePath = examplePath,
            userPath = listOf(userPath)
        )
        val accuracy = calculator.calculateResult(
            drawResult,
            strokeWidthUser = 1
        ){
            context.saveBitmapToFile(it, "test_calculate_result_eye.png")
        }

        assertEquals(50, accuracy)

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