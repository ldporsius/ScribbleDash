package nl.codingwithlinda.scribbledash.e2e

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.MainActivity
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.resourceToDrawPaths
import nl.codingwithlinda.scribbledash.core.data.util.toBitmapUiOnly
import org.junit.Rule
import org.junit.Test

class OneRoundWonderE2ETest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private val robot = TestRobot(composeRule)

    private val example by lazy { resourceToDrawPaths(R.drawable.book, composeRule.activity)}
    private val userDrawEvents by lazy {
        example.map {
            pathToCoordinates(it)
        }.map { pathCoordinates ->
            pathCoordinates.map {
                Offset(it.x, it.y)
            }
        }
    }

    private var offsetRemaining: Int = 0

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testOneRoundWonder(): Unit = runBlocking {

        offsetRemaining = userDrawEvents.flatten().size

        robot.waitForNodeWithContentDescription("home")

        robot.clickNodeWithText("round")
        delay(1000)
        robot.clickNodeWithText("beginner")
        delay(1000)

        composeRule.waitUntil(
            timeoutMillis = 5000,
            condition = {
                composeRule.onNodeWithText("0", substring = true).isDisplayed()
            }
        )

        val bm = example.toBitmapUiOnly(500, 2f)
        val bmDrawer = AndroidBitmapPrinter(composeRule.activity)
        bmDrawer.printBitmap(bm, "test_e2e_bitmap.png")

        composeRule.waitUntil(
            timeoutMillis = 5000,
            condition = {
                composeRule.onNodeWithContentDescription("canvas", true, true).isDisplayed()
            }
        )
        println("offsets at start: $offsetRemaining")
        runBlocking {
            robot.performDragEvents(userDrawEvents.flatten())
        }

        composeRule.waitUntilExactlyOneExists(
            hasText("Done", true, true) and hasClickAction() and isEnabled(),
            timeoutMillis = 5000
        )
       robot.saveScreenshot("scribble_e2e.png")

        robot.clickNodeWithText("Done")
        robot.waitForNodeWithText("example")
        robot.waitForNodeWithText("drawing")

        robot.saveScreenshot("scribble_e2e_result.png")

        delay(1000)
    }
}