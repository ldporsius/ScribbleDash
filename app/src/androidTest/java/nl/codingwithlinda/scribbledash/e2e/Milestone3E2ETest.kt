package nl.codingwithlinda.scribbledash.e2e

import android.graphics.Path
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.size
import androidx.compose.ui.unit.width
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.MainActivity
import nl.codingwithlinda.scribbledash.core.application.ScribbleDashDebugApplication
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.e2e.util.offsetPath
import org.junit.Rule
import org.junit.Test

class Milestone3E2ETest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private val robot = TestRobot(composeRule)
    val appmodule = ScribbleDashDebugApplication.appModule
    val spGameEngine = appmodule.gameEngine(GameMode.SPEED_DRAW)

    var bounds = DpRect(DpOffset.Zero, DpSize.Zero)
    fun exampleBounds(paths: List<Path>) = combinedPath(paths).asComposePath().getBounds()

    @Test
    fun milestone3Test(): Unit = runBlocking{
        delay(2000)
        robot.clickNodeWithText("speed")
        .clickNodeWithText("beginner")
            .waitForNodeWithText("0 seconds left")
            .waitForNodeWithContentDescription("canvas")


        composeRule.onNodeWithContentDescription("canvas")
            .assertIsDisplayed()
            .assertHeightIsAtLeast(300.dp)
            .getBoundsInRoot().also {
                println("BOUNDS: $it")
                bounds = it
            }



        val jobs = launch {
            repeat(10) {
                robot.waitForNodeWithContentDescription("canvas")
                drawOneExample()
                robot
                    .waitForButtonEnabled("Done")
                    .clickNodeWithText("Done")

            }
        }

        jobs.join()

        robot.waitForAtLeastOneWithText("00:00")

    }

    suspend fun drawOneExample(){

        val example = spGameEngine.getResult().examplePath
        println("EXAMPLE num paths: ${example.size}")
        val sf = bounds.width.value / exampleBounds(example).width
        println("SCALE FACTOR: $sf")

        example.onEach{
            it.asComposePath().transform(
                Matrix().apply {
                    this.scale(x = sf, y = sf, z= 1f,)
                }
            )
        }

        val exampleBounds = exampleBounds(example)
        println("PARENT BOUNDS: $bounds , CenterX: ${bounds.size.width.value / 2}, centerY: ${bounds.size.height.value / 2}")
        println("EXAMPLE BOUNDS: ${exampleBounds} , centerX: ${exampleBounds.width / 2}, centerY: ${exampleBounds.height / 2}")
        val tx =  - exampleBounds.width
        println("TX: $tx")

        val ty = - exampleBounds.height
        println("TY: $ty")

        example.onEach {
            robot.performDragEvents(
                offsetPath(it, tx, ty)
            )
        }
    }
}