package nl.codingwithlinda.scribbledash.e2e

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.size
import androidx.test.ext.junit.rules.ActivityScenarioRule
import kotlinx.coroutines.delay
import nl.codingwithlinda.scribbledash.MainActivity
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter

class TestRobot(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    @OptIn(ExperimentalTestApi::class)
    fun waitForAtLeastOneWithText(text: String): TestRobot{
        testRule.waitUntilAtLeastOneExists(
            hasText(text, true, true),
            5000
        )
        return this
    }
    fun waitForNodeWithText(text: String): TestRobot{

        testRule.waitUntil(
            timeoutMillis = 5000,
            condition = {
                testRule.onNodeWithText(text, true, true).isDisplayed()
            }
        )

        return this
    }
    fun clickNodeWithText(text: String): TestRobot {
        testRule.waitForIdle()
        testRule.onNodeWithText(text, true, true).performClick()
        return this
    }

    fun waitForNodeWithContentDescription(contentDescription: String): TestRobot{
        testRule.waitForIdle()
        testRule.waitUntil(
            timeoutMillis = 5000,
            condition = {
                testRule.onNodeWithContentDescription(contentDescription, true, true).isDisplayed()
            }
        )

        return this

    }
    fun clickNodeWithContentDescription(contentDescription: String): TestRobot{
        testRule.waitForIdle()
        testRule.onNodeWithContentDescription(contentDescription, true, true).performClick()
        return this

    }

    suspend fun performDragEvents(events: List<Offset>): Boolean{

        println("EVENTS: $events")

        testRule.onNodeWithContentDescription("canvas")
            .performTouchInput {
            down(events.first())
        }
        (1 until  events.size).onEach {i ->

            testRule.onNodeWithContentDescription("canvas")
                .assertIsDisplayed()
                .performTouchInput {
                        moveTo(events[i] + center, 0)
                }
            delay(10)
        }
        testRule
            .onNodeWithContentDescription("canvas")
            .assertIsDisplayed()
            .performTouchInput {
            up()
        }
        return true
    }

    fun saveScreenshot(name: String){
        val bm = testRule.onRoot().captureToImage().asAndroidBitmap()
        val bmDrawer = AndroidBitmapPrinter(testRule.activity)
        bmDrawer.printBitmap(bm, name)
    }
}