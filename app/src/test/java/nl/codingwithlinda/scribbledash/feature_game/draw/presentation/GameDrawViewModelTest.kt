package nl.codingwithlinda.scribbledash.feature_game.draw.presentation

import androidx.compose.ui.geometry.Offset
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nl.codingwithlinda.scribbledash.core.application.ScribbleDashApplication.Companion.appModule
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.OneRoundGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.test_data.FakeExampleProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameDrawViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val careTaker = PathDataCareTaker()
    private val offsetParser = AndroidOffsetParser
    private val pathDrawer = StraightPathDrawer()
    private val exampleProvider = FakeExampleProvider()

    private lateinit var gameEngine: GameEngine
    private lateinit var viewModel: GameDrawViewModel


    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)

        gameEngine = OneRoundGameEngine(
            exampleProvider = exampleProvider,
            offsetParser = offsetParser,
            pathDrawer = pathDrawer
        )
        viewModel = GameDrawViewModel(
            gameEngine = gameEngine,
            careTaker = careTaker,
            offsetParser = offsetParser,
            pathDrawer = pathDrawer,
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

    @Test
    fun `test undo in viewmodel, can undo is false after five undoes`() = runTest(dispatcher) {

        viewModel.uiState.test {

            repeat(9) {
                viewModel.handleAction(DrawAction.StartPath(Offset(1f, 1f)))

                val em1 = awaitItem()
                //println("EM1: $em1")

                viewModel.handleAction(DrawAction.Draw(Offset(2f, 2f)))

                val em2 = awaitItem()
                //println("EM2: $em2")

                viewModel.handleAction(DrawAction.Save)
                val em3 = awaitItem()
                //println("EM3: $em3")
            }

            repeat(5) {
                viewModel.handleAction(DrawAction.Undo)
                val item = awaitItem()
                println("ITEM $it is undo available: ${item.isUndoAvailable()}")

            }
            awaitItem()
            val em4 = awaitItem()
            println("EM4: $em4")
            assertEquals(false, em4.isUndoAvailable())


            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test undo in viewmodel, can undo is true after five undoes and one redo`() = runTest(dispatcher) {

        viewModel.uiState.test {

            repeat(9) {
                viewModel.handleAction(DrawAction.StartPath(Offset(1f, 1f)))

                val em1 = awaitItem()
                println("EM1: $em1")

                viewModel.handleAction(DrawAction.Draw(Offset(2f, 2f)))

                val em2 = awaitItem()
                println("EM2: $em2")

                viewModel.handleAction(DrawAction.Save)
                awaitItem()

            }
            val em3 = awaitItem()
            println("EM3: $em3")

            assertEquals(true, em3.isUndoAvailable())

            repeat(5) {
                viewModel.handleAction(DrawAction.Undo)
                awaitItem()
            }


            val item = awaitItem()

            println("ITEM is undo available: ${item.isUndoAvailable()}")
            assertEquals(false, item.isUndoAvailable())



            viewModel.handleAction(DrawAction.Redo)
            awaitItem()
            val em5 = awaitItem()
            println("EM5: $em5")
            assertEquals(true, em5.isUndoAvailable())

            cancelAndConsumeRemainingEvents()
        }
    }

}