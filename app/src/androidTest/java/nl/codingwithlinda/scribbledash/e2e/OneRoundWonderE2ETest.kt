package nl.codingwithlinda.scribbledash.e2e

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.size
import androidx.compose.ui.unit.width
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.MainActivity
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.resourceToDrawPaths
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.data.util.toBitmapUiOnly
import nl.codingwithlinda.scribbledash.e2e.util.offsetPath
import org.junit.Rule
import org.junit.Test

class OneRoundWonderE2ETest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private val robot = TestRobot(composeRule)

    private val example by lazy { resourceToDrawPaths(R.drawable.book, composeRule.activity)}

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testOneRoundWonder(): Unit = runBlocking {

        var bounds = DpRect(DpOffset.Zero, DpSize.Zero)
        fun exampleBounds() = combinedPath(example).asComposePath().getBounds()
        fun scaleFactor() = bounds.width.value / exampleBounds().width

        robot.waitForNodeWithContentDescription("home")

        robot.clickNodeWithText("round")
        delay(1000)
        robot.clickNodeWithText("beginner")

        composeRule.waitUntil(
            timeoutMillis = 5000,
            condition = {
                composeRule.onNodeWithText("0", substring = true).isDisplayed()
            }
        )

        composeRule.waitUntil(
            timeoutMillis = 5000,
            condition = {
                composeRule.onNodeWithContentDescription("canvas", true, true).isDisplayed()
            }
        )
        composeRule.onNodeWithContentDescription("canvas")
            .assertIsDisplayed()
            .assertHeightIsAtLeast(300.dp)
            .getBoundsInRoot().also {
                println("BOUNDS: $it")
                bounds = it
            }

        val jobs = launch {
            val sf = scaleFactor()
            println("SCALE FACTOR: $sf")

           example.onEach{
                it.asComposePath().transform(
                    Matrix().apply {
                        this.scale(x = sf, y = sf, z= 1f,)
                        //this.translate(x = tx)
                    }
                )
            }

            println("PARENT BOUNDS: $bounds , CenterX: ${bounds.size.width.value / 2}, centerY: ${bounds.size.height.value / 2}")
            println("EXAMPLE BOUNDS: ${exampleBounds()} , centerX: ${exampleBounds().width / 2}, centerY: ${exampleBounds().height / 2}")
            val tx =  - exampleBounds().width
            println("TX: $tx")

            val ty = - exampleBounds().height
            println("TY: $ty")

            (0 until  example.size).map{i ->
                robot.performDragEvents(
                       offsetPath(example[i], tx, ty)
                   )
                }
        }

        jobs.join()

        composeRule.waitUntilExactlyOneExists(
            hasText("Done", true, true) and hasClickAction() and isEnabled(),
            timeoutMillis = 15000
        )

       robot.saveScreenshot("scribble_e2e.png")

        robot.clickNodeWithText("Done")
        .waitForNodeWithText("example")
        .waitForAtLeastOneWithText("drawing")

        .saveScreenshot("scribble_e2e_result.png")

        delay(15000)
    }
}