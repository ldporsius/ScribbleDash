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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.state.DrawExampleUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.state.SpeedDrawUiState

class SpeedDrawViewModel(
    private val gameEngine: GameEngineTemplate,
    private val navToResult: () -> Unit
): ViewModel() {


    private val _topBarUiState = MutableStateFlow(SpeedDrawUiState())
    val topBarUiState = _topBarUiState.asStateFlow()

    private var countDownJob: Job? = null
    init {

        viewModelScope.launch {
            gameEngine.drawingTimeCounter.startCountdown()
        }
        gameEngine.shouldShowExample.onEach { show ->
            if (show) gameEngine.pauseGame() else gameEngine.resumeGame()

            val state = if (show) DrawState.EXAMPLE else DrawState.USER_INPUT
            _topBarUiState.update {
                it.copy(
                    drawState = state
                )
            }
        }.launchIn(viewModelScope)

        collectSpeedDrawCountdown()

        countDownJob?.invokeOnCompletion {
            println("SpeedDrawViewModel countdown finished. $it")
            navToResult()
        }
    }


    private fun collectSpeedDrawCountdown() = viewModelScope.launch {

        countDownJob = gameEngine.drawingTimeCounter.timer.onEach { count ->
            _topBarUiState.update {
                it.copy(
                    timeLeftSeconds = count
                )
            }
            if (count == 0){
                delay(100)
                countDownJob?.cancel()
            }
        }.launchIn(viewModelScope)
    }



    fun onDone(){
        viewModelScope.launch {

            val success = gameEngine.isGameSuccessful()
            val successCount = if(success) 1 else 0

            _topBarUiState.update {
                it.copy(
                    successes = it.successes + successCount
                )
            }
        }
    }


}