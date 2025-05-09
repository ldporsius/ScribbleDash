package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import androidx.compose.ui.geometry.Offset
import app.cash.turbine.test
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.di.TestAppModule
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.EndlessGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EndlessDrawViewModelTest{

    private lateinit var viewModel: EndlessDrawViewModel

    private val appModule = TestAppModule()

    private val gameEngine = EndlessGameEngine(
        exampleProvider = appModule.drawExampleProvider,
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
            val userIput = result.examplePath.map {
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