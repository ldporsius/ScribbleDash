package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownSpeedDraw
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.state.DrawExampleUiState

class SpeedDrawViewModel(
    private val exampleProvider: AndroidDrawExampleProvider

): ViewModel(

) {
    private val _exampleCountdown = CountDownTimer()
    private val _speedDrawCountdown = CountDownSpeedDraw()

    private val examples = exampleProvider.examples.shuffled()
    private val exampleIndex = MutableStateFlow<Int>(0)

    private val _topBarUiState = MutableStateFlow(SpeedDrawUiState())
    val topBarUiState = _topBarUiState.asStateFlow()


    private val _exampleUiState = MutableStateFlow(DrawExampleUiState())
    val exampleUiState = combine( _exampleUiState, exampleIndex){state, index ->

                state.copy(
                    drawPaths = listOf( examples.get(index).path)
                )

        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _exampleUiState.value)

    init {
       resetExampleCountdown()

        collectSpeedDrawCountdown()
    }

    private fun collectSpeedDrawCountdown(){
        _speedDrawCountdown.startCountdown.onEach { count ->
            _topBarUiState.update {
                it.copy(
                    timeLeft = count.toString(),
                    timeLeftSeconds = count
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onDone(){
        _speedDrawCountdown.pause()

        _topBarUiState.update {
            it.copy(
                drawState = DrawState.EXAMPLE
            )
        }

        startNewExample()
        resetExampleCountdown()
    }

    private fun startNewExample(){
        exampleIndex.update {current ->
            val update = maxOf(-1, examples.lastIndex - current)
            println("SPEED DRAW VIEWMODEL update: $update")
            update - 1
        }

        ResultManager.INSTANCE.getLastResult()?.let {
            ResultManager.INSTANCE.updateResult(
                it.copy(
                    examplePath = listOf(examples.get(exampleIndex.value))
                )
            )
        }
    }

    private fun resetExampleCountdown(){
        _speedDrawCountdown.pause()
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

                _speedDrawCountdown.resume()

            }
        }.launchIn(viewModelScope)
    }
}