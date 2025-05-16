package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.state.SpeedDrawUiState

class SpeedDrawViewModel(
    private val gameEngine: GameEngineTemplate,
    private val navToResult: () -> Unit
): ViewModel() {


    private val _topBarUiState = MutableStateFlow(SpeedDrawUiState())
    val topBarUiState = _topBarUiState
        .onStart {
            gameEngine.drawingTimeCounter.reset()

        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SpeedDrawUiState())

    private var countDownJob: Job? = null
    init {
        gameEngine.drawingTimeCounter.reset()

        viewModelScope.launch {
            gameEngine.drawingTimeCounter.startCountdown()
        }
        gameEngine.shouldShowExample.onEach { show ->
            if (show) gameEngine.pauseGame() else gameEngine.resumeGame()

        }.launchIn(viewModelScope)

        collectSpeedDrawCountdown()
    }


    private fun collectSpeedDrawCountdown() = viewModelScope.launch {

        countDownJob = gameEngine.drawingTimeCounter.timer.onEach { count ->
            _topBarUiState.update {
                it.copy(
                    timeLeftSeconds = count
                )
            }
            if (count == 0){
                gameEngine.persistResult()
                delay(100)
                navToResult()
            }
        }.launchIn(viewModelScope)
    }

    fun stopCountdown(){
        countDownJob?.cancel()
    }

    fun onDone(){
        viewModelScope.launch {

            val numberSuccesses = gameEngine.numberSuccessesForLatestGame()
            println("SPEED DRAW VIEW MODEL Has Successes: $numberSuccesses")

            _topBarUiState.update {
                it.copy(
                    successes = numberSuccesses
                )
            }
        }
    }
}