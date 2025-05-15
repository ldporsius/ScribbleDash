package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.test.AndroidTestAppModule
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.EndlessGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EndlessDrawViewModelTest{

    private val context = ApplicationProvider.getApplicationContext<Application>()
    private lateinit var viewModel: EndlessDrawViewModel

    private val appModule = AndroidTestAppModule(context)
    private val gamesManager = appModule.gamesManager

    private val gameEngine = EndlessGameEngine(
        exampleProvider = appModule.drawExampleProvider,
        gamesManager = gamesManager
    )

    @Before
    fun setup(){
        viewModel = EndlessDrawViewModel(
            gameEngine = gameEngine,
        )
    }

    @Test
    fun testEndlessDrawViewModel() = runBlocking {
        viewModel.endlessUiState.test {

            val result = gameEngine.getResult()
            val userIput = result.examplePath.map {
                pathToCoordinates(it, 5f)
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

            val finalItem = awaitItem()

            println("final emission: $finalItem")
            assertEquals(initialItem.numberSuccess, DrawState.EXAMPLE)
            assertEquals(1, finalItem.numberSuccess)
        }
    }
}