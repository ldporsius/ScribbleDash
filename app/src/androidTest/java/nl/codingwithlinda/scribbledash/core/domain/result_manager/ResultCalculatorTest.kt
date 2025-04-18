package nl.codingwithlinda.scribbledash.core.domain.result_manager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.saveBitmapToFile
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawable
import org.junit.Assert.*
import org.junit.Test

class ResultCalculatorTest{

    val calculator = ResultCalculator()
    val context = ApplicationProvider.getApplicationContext<Context>()

    val examplePath = testExampleDrawable(context, R.drawable.alien)
    val drawResult = DrawResult(
        id = "",
        level = GameLevel.MASTER,
        examplePath = examplePath,
        userPath = listOf(examplePath)
    )

    @Test
    fun testCalculateResult_samePathAccuracy100(){
        val accuracy = calculator.calculateResult(
            drawResult,
            strokeWidthUser = 1
        ){
            context.saveBitmapToFile(it, "test_calculate_result.png")
        }

        assertEquals(100, accuracy)

    }

    @Test
    fun testCalculateResult_samePathDifferentStrokeWidthAccuracy100(){
        val accuracy = calculator.calculateResult(
            drawResult.copy(
                level = GameLevel.BEGINNER
            ),
            1
        ){
            context.saveBitmapToFile(it, "test_calculate_result.png")
        }

        assertEquals(100, accuracy)

    }
}