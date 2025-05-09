package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.state.EndlessUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.state.DrawExampleUiState

class EndlessDrawViewModel(
    private val gamesManager: GamesManager,
    private val gameEngine: GameEngine
): ViewModel() {

    private val _endlessUiState = MutableStateFlow(EndlessUiState())
    val endlessUiState = _endlessUiState.asStateFlow()

    private val _exampleUiState = MutableStateFlow(DrawExampleUiState())
    val exampleUiState = _exampleUiState.asStateFlow()

    init {
       startNewGame()
    }

    private fun startNewGame(){
        viewModelScope.launch {
            val numberSuccesses = gamesManager.numberSuccessesForLatestGame(GameMode.ENDLESS_MODE)

            _endlessUiState.update {
                it.copy(
                    numberSuccess = numberSuccesses
                )
            }
        }
        _exampleUiState.update { uiState ->
            val update = gameEngine.provideExample().examplePath

            uiState.copy(
                drawPaths = update
            )
        }

        _endlessUiState.update {
            it.copy(
                drawState = DrawState.EXAMPLE
            )
        }
        collectCountDown()
    }

    private fun collectCountDown(){
        gameEngine.countDown.onEach { count->
            _exampleUiState.update {
                it.copy(
                    counter = count
                )
            }
            if (count == 0){
                _endlessUiState.update {
                    it.copy(
                        drawState = DrawState.USER_INPUT
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onDone(){
        viewModelScope.launch {
            gameEngine.getResult().let { result ->
                gamesManager.updateLatestGame(
                    GameMode.ENDLESS_MODE,
                    listOf(result)
                )
            }

            gameEngine.endUserInput {res ->
                when(res){
                    is ScResult.Failure -> {
                        println("ENDLESS DRAW VM. error: ${res.error}")
                    }
                    is ScResult.Success -> {
                        println("ENDLESS DRAW VM. success")
                        startNewGame()
                    }
                }
            }

        }
    }
}