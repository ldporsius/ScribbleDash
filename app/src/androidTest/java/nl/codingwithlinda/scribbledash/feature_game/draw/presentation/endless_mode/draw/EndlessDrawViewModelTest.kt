package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import app.cash.turbine.test
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.core.di.TestAppModule
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.core.test.fakeDrawResultSamePaths
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EndlessDrawViewModelTest{

    private lateinit var viewModel: EndlessDrawViewModel

    private val appModule = TestAppModule()

    @Before
    fun setup(){
        viewModel = EndlessDrawViewModel(
            exampleProvider = appModule.drawExampleProvider,
            gamesManager = appModule.gamesManager
        )

    }

    @Test
    fun testEndlessDrawViewModel() = runBlocking {
        viewModel.endlessUiState.test {

            val result = fakeDrawResultSamePaths()
            ResultManager.INSTANCE.addResult(GameLevel.BEGINNER)
            ResultManager.INSTANCE.updateResult(result)

            val initialItem = awaitItem()

            viewModel.onDone()

            val finalItem = awaitItem()

            println("final emission: $finalItem")
            assertEquals(initialItem.drawState, DrawState.EXAMPLE)
            assertEquals(finalItem.numberSuccess, 1)
        }
    }
}