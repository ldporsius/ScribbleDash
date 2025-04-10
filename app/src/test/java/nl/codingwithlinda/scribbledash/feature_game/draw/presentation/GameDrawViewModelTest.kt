package nl.codingwithlinda.scribbledash.feature_game.draw.presentation

import androidx.compose.ui.geometry.Offset
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.test_data.FakePathDrawer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameDrawViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val careTaker = PathDataCareTaker()
    private lateinit var viewModel: GameDrawViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)

        viewModel = GameDrawViewModel(
            careTaker = careTaker
        )
    }
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `test offsets in viewmodel after undo`() = runTest(dispatcher) {

        viewModel.uiState.test {

            viewModel.handleAction(DrawAction.StartPath(Offset(1f, 1f)))

            val em1 = awaitItem()
            println("EM1: $em1")

            viewModel.handleAction(DrawAction.Draw(Offset(2f, 2f)))

            val em2 = awaitItem()
            println("EM2: $em2")

            viewModel.handleAction(DrawAction.Save)
            val em3 = awaitItem()
            println("EM3: $em3")

            viewModel.handleAction(DrawAction.Undo)
            val item = awaitItem()

            println("ITEM: $item")
            assertEquals(1, item.drawPaths.size)

            val em4 = awaitItem()
            println("EM4: $em4")

            assertEquals(0, em4.drawPaths.size)

            cancelAndConsumeRemainingEvents()


        }
    }

    @Test
    fun `test offsets in viewmodel, save many, undo one`() = runTest(dispatcher) {

        viewModel.uiState.test {

            repeat(9) {
                viewModel.handleAction(DrawAction.StartPath(Offset(1f, 1f)))

                val em1 = awaitItem()
                println("EM1: $em1")

                viewModel.handleAction(DrawAction.Draw(Offset(2f, 2f)))

                val em2 = awaitItem()
                println("EM2: $em2")

                viewModel.handleAction(DrawAction.Save)
                val em3 = awaitItem()
                println("EM3: $em3")
            }

            viewModel.handleAction(DrawAction.Undo)
            val item = awaitItem()

            println("ITEM: $item")
            assertEquals(9, item.drawPaths.size)

            val em4 = awaitItem()
            println("EM4: $em4")

            assertEquals(8, em4.drawPaths.size)

            cancelAndConsumeRemainingEvents()


        }
    }




}