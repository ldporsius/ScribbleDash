package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.state.DrawExampleUiState

class SpeedDrawViewModel: ViewModel() {
    private val _exampleCountdown = CountDownTimer()

    private val _topBarUiState = MutableStateFlow(SpeedDrawUiState())
    val topBarUiState = _topBarUiState.asStateFlow()


    private val _exampleUiState = MutableStateFlow(DrawExampleUiState())
    val exampleUiState = _exampleUiState.asStateFlow()

    init {
        _exampleCountdown.startCountdown().onEach { count ->
            _exampleUiState.update {
                it.copy(
                    counter = count
                )
            }
            if (count == 0){
                _topBarUiState.update {
                    it.copy(
                        drawState = DrawState.USER_INPUT
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun handleAction(action: DrawAction){

    }
}