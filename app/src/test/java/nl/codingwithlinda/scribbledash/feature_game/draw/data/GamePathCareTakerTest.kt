package nl.codingwithlinda.scribbledash.feature_game.draw.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import app.cash.turbine.test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GamePathCareTakerTest{

    private val careTaker = GamePathCareTaker()

    @Test
    fun testEmpty() = runTest {
        careTaker.undoStack.test {
            val item = awaitItem()
            assertEquals(0, item.size)
            cancelAndConsumeRemainingEvents()
        }
        careTaker.redoStack.test {
            val item = awaitItem()
            assertEquals(0, item.size)
            cancelAndConsumeRemainingEvents()
        }
    }
    @Test
    fun testSave() = runTest {
        val path = ColoredDrawPath(
            color = Color.Black,
            path = Path()
        )

        repeat(6){
            careTaker.save(path)
        }
        careTaker.undoStack.first().let {
            assertEquals(5, it.size)
        }

        careTaker.undoStack.test{
            val em0 = awaitItem()
            println("em0 $em0")

            val item = careTaker.undo()
            println("item $item")

            val em1 = awaitItem()
            println("em1 $em1")
            assertEquals(4, em1.size)

            cancelAndConsumeRemainingEvents().also {
                println("it $it")

            }
        }
    }

    @Test
    fun testRedo() = runTest {
        val path = ColoredDrawPath(
            color = Color.Black,
            path = Path()
        )

        repeat(6){
            careTaker.save(path)
        }
        careTaker.redoStack.first().let {
            assertEquals(0, it.size)
        }

        careTaker.redoStack.test{
            val em0 = awaitItem()
            println("em0 $em0")

            val item = careTaker.undo()
            println("item $item")

            val em1 = awaitItem()
            println("em1 $em1")
            assertEquals(1, em1.size)

            cancelAndConsumeRemainingEvents().also {
                println("it $it")

            }
        }
    }


}