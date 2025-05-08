package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import androidx.compose.ui.geometry.Offset
import app.cash.turbine.test
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.di.TestAppModule
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.core.test.fakeDrawResultSamePaths
import nl.codingwithlinda.scribbledash.core.test.fakePathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.EndlessGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EndlessDrawViewModelTest{

    private lateinit var viewModel: EndlessDrawViewModel

    private val appModule = TestAppModule()

    val offsetParser = AndroidOffsetParser
    val pathDrawer = StraightPathDrawer()

    private val gameEngine = EndlessGameEngine(
        exampleProvider = appModule.drawExampleProvider,
        offsetParser = offsetParser,
        pathDrawer = pathDrawer
    )

    @Before
    fun setup(){
        viewModel = EndlessDrawViewModel(
            gameEngine = gameEngine,
            gamesManager = appModule.gamesManager
        )
    }

    @Test
    fun testEndlessDrawViewModel() = runBlocking {
        viewModel.endlessUiState.test {

            val result = gameEngine.provideExample()
            val userIput = result.examplePath.map { it.path }.map {
                pathToCoordinates(it)
            }.flatten()
                .map {coor ->
                   Offset(coor.x, coor.y)
                }

            assertTrue(userIput.isNotEmpty())
            val pathData = PathData(
                id = "",
                color = 0,
                path = userIput
            )
            gameEngine.processUserInput(
                listOf( pathData)
            )

            val initialItem = awaitItem()

            viewModel.onDone()

            val finalItem = awaitItem()

            println("final emission: $finalItem")
            assertEquals(initialItem.drawState, DrawState.EXAMPLE)
            assertEquals(1, finalItem.numberSuccess)
        }
    }
}