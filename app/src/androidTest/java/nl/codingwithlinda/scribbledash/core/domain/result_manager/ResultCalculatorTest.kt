package nl.codingwithlinda.scribbledash.core.domain.result_manager

import android.app.Application
import android.graphics.Path
import android.graphics.PathMeasure
import androidx.test.core.app.ApplicationProvider
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.util.saveBitmapToFile
import nl.codingwithlinda.scribbledash.core.data.util.toBitmap
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawable
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultCalculatorTest{

    private val calculator = ResultCalculator
    private val context: Application = ApplicationProvider.getApplicationContext<Application>()
    private val provider = AndroidDrawExampleProvider.getInstance(context)

    @Test
    fun testCalculateResult_samePathAccuracy100(){
        val examplePath = testExampleDrawable(context, R.drawable.eye).examplePath
        val drawResult = DrawResult(
            id = "",
            level = GameLevel.BEGINNER,
            examplePath = examplePath,
            userPath = examplePath
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
        val userPath = testExampleDrawable(context, R.drawable.eye).examplePath

        val examplePath = List(2){
            userPath
        }.flatten()

        examplePath.onEach {
            println("-- in test half length path --. examplePath.size = ${getPathLength(it)}")

        }
        examplePath.fold(0f) { acc, androidDrawPath ->
            getPathLength(androidDrawPath) + acc
        }.also {
            println("-- in test half length path --. total length = $it")

        }
        val drawResult = DrawResult(
            id = "",
            level = GameLevel.BEGINNER,
            examplePath = examplePath,
            userPath = userPath
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
            val  examplePath= provider.examples[i].examplePath

            val origBm = examplePath.toBitmap(
                500, 2f,
                basisStrokeWidth = 4f,
            )
            context.saveBitmapToFile(origBm, "orig_bm_$i.png")

            val androidDrawResult = DrawResult(
                id = "",
                level = GameLevel.BEGINNER,
                examplePath = examplePath,
                userPath = examplePath
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