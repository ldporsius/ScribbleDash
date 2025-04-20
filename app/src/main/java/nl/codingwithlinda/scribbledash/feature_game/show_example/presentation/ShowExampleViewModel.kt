package nl.codingwithlinda.scribbledash.feature_game.show_example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.state.DrawExampleUiState

class ShowExampleViewModel(
    private val exampleProvider: AndroidDrawExampleProvider,
    navToDraw: () -> Unit
): ViewModel() {

    private val countDownTimer = CountDownTimer()

    private val _uiState = MutableStateFlow(DrawExampleUiState())
    val uiState = _uiState.combine(countDownTimer.startCountdown()){
        state, time ->
        state.copy(
            counter = time
        )

    }.onEach {
        if (it.counter == 0){
            navToDraw()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        val index = (0 until exampleProvider.examples.size).random()
        val _example = exampleProvider.examples.get(index)

        _example.also {example ->

            ResultManager.INSTANCE.let {manager ->
                manager.getLastResult()?.let {lastResult ->
                    manager.updateResult(
                        lastResult.copy(
                            examplePath = listOf( example)
                    )
                    )
                }
            }
            _uiState.update {
                it.copy(
                    drawPaths = listOf(example.path)
                )
            }



        }
    }

    fun getGlasses(): AndroidDrawPath {
        return exampleProvider.getByResId(R.drawable.glasses)
    }

    fun forceUmbrella(): AndroidDrawPath {
       return exampleProvider.getByResId(R.drawable.umbrella)
    }
}