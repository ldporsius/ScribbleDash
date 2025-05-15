package nl.codingwithlinda.scribbledash.e2e

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.MainActivity
import org.junit.Rule
import org.junit.Test

class Milestone3E2ETest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private val robot = TestRobot(composeRule)


    @Test
    fun milestone3Test(): Unit = runBlocking{
        delay(2000)
        robot.clickNodeWithText("speed")

        delay(5000)
        robot.clickNodeWithText("beginner")
            .waitForNodeWithText("0 seconds left")
    }
}