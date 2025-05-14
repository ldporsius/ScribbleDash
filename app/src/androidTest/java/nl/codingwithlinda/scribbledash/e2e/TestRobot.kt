package nl.codingwithlinda.scribbledash.e2e

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import nl.codingwithlinda.scribbledash.MainActivity
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter

class TestRobot(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

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

    fun performDragEvents(events: List<Offset>): TestRobot{

        println("EVENTS: $events")


        testRule.onNodeWithContentDescription("canvas")
            .assertIsDisplayed()
            .performTouchInput {
            down(events.first())
        }
        events.onEach {offset ->
            testRule.onNodeWithContentDescription("canvas")
                .assertIsDisplayed()
                .performTouchInput {
                        moveTo(offset, 0)
                }
        }
        testRule
            .onNodeWithContentDescription("canvas")
            .assertIsDisplayed()
            .performTouchInput {
            up()
        }
        return this
    }

    fun saveScreenshot(name: String){
        val bm = testRule.onRoot().captureToImage().asAndroidBitmap()
        val bmDrawer = AndroidBitmapPrinter(testRule.activity)
        bmDrawer.printBitmap(bm, name)
    }
}