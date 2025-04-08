package nl.codingwithlinda.scribbledash.feature_game.draw.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GamePathsCareTakerTest{

    private val careTaker = GamePathsCareTaker()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test save`() = runTest {
        val path = ColoredDrawPath(
            color = Color.Black,
            path = Path()
        )
        careTaker.undoStack.test {
            awaitItem()
            repeat(6) {
                careTaker.save(listOf(path))
            }

            advanceUntilIdle()
            awaitItem()
            val em0 = awaitItem()
            val em1 = awaitItem()
            val em2 = awaitItem()
            val em3 = awaitItem()
            val em4 = awaitItem()
            println("em4 $em4")
            assertEquals(6, em4.size)

            careTaker.undo()
            val undoEm = awaitItem()
            assertEquals(5, undoEm.size)

            careTaker.redo()
            val redoEm = awaitItem()
            assertEquals(6, redoEm.size)

        }
    }
}