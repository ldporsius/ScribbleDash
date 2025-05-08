package nl.codingwithlinda.scribbledash.core.domain.result_manager

import android.app.Application
import android.graphics.Path
import android.graphics.PathMeasure
import androidx.test.core.app.ApplicationProvider
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.saveBitmapToFile
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.toBitmap
import nl.codingwithlinda.scribbledash.core.domain.model.AndroidDrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawable
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping.coordinatesToPath
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultCalculatorTest{

    private val calculator = ResultCalculator
    private val context: Application = ApplicationProvider.getApplicationContext<Application>()
    private val provider = AndroidDrawExampleProvider.getInstance(context)

    @Test
    fun testCalculateResult_samePathAccuracy100(){
        val examplePath = testExampleDrawable(context, R.drawable.eye)
        val drawResult = AndroidDrawResult(
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
        val pathMeasure = PathMeasure(path, true)
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
        val drawResult = AndroidDrawResult(
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

        repeat(34) { i ->
            val  examplePath= provider.examples[i]

            val androidPath = coordinatesToPath(examplePath.path)
            val origBm = listOf( androidPath.path).toBitmap(
                500, 2f,
                basisStrokeWidth = 4f,
            )
            context.saveBitmapToFile(origBm, "orig_bm_$i.png")

            val androidDrawResult = AndroidDrawResult(
                id = "",
                level = GameLevel.BEGINNER,
                examplePath = listOf(androidPath),
                userPath = listOf(androidPath)
            )
            val accuracy = calculator.calculateResult(
                androidDrawResult,
                1
            ) {
                context.saveBitmapToFile(it, "test_calculate_result_$i.png")
            }

            assertEquals(100, accuracy)

        }
    }
}