package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.state.EndlessUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.state.DrawExampleUiState
import nl.codingwithlinda.scribbledash.feature_game.show_example.domain.usecase.SaveRandomExampleUseCase

class EndlessDrawViewModel(
    private val exampleProvider: AndroidDrawExampleProvider,
    private val gamesManager: GamesManager
): ViewModel() {

    private val saveRandomExampleUseCase = SaveRandomExampleUseCase(exampleProvider)

    private val counter = CountDownTimer()

    private val _endlessUiState = MutableStateFlow(EndlessUiState())
    val endlessUiState = _endlessUiState.asStateFlow()

    private val _exampleUiState = MutableStateFlow(DrawExampleUiState())
    val exampleUiState = _exampleUiState.asStateFlow()

    init {
       startNewGame()
    }

    fun startNewGame(){
        saveRandomExampleUseCase.example()

        _exampleUiState.update {
            val update =  ResultManager.INSTANCE.getLastResult()?.examplePath?.map {
                it.path
            } ?: emptyList()

            it.copy(
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
        counter.startCountdown().onEach { count->
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
            ResultManager.INSTANCE.getLastResult()?.let { result ->
                gamesManager.updateLatestGame(
                    GameMode.ENDLESS_MODE,
                    listOf(result)
                )
            }
           val numberSuccesses = gamesManager.numberSuccessesForLatestGame(GameMode.ENDLESS_MODE)

            _endlessUiState.update {
                it.copy(
                    numberSuccess = numberSuccesses
                )
            }
        }
    }
}