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
            awaitItem()
            viewModel.handleAction(DrawAction.StartPath(Offset(1f, 1f)))
            viewModel.handleAction(DrawAction.Draw(Offset(2f, 2f)))

            val em1 = awaitItem()
            println("EM1: $em1")

            viewModel.handleAction(DrawAction.Undo)
            val item = awaitItem()

            assertEquals(0, item.drawPaths.size)

            cancelAndConsumeRemainingEvents()


        }
    }



}