package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.core.domain.util.BitmapPrinter
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownSpeedDraw
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.usecase.SpeedDrawIsSuccessUseCase
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.state.DrawExampleUiState

class SpeedDrawViewModel(
    private val exampleProvider: AndroidDrawExampleProvider,
    private val resultCalculator: ResultCalculator,
    private val bitmapPrinter: BitmapPrinter,
    private val navToResult: () -> Unit
): ViewModel() {
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

    private var countDownJob: Job? = null
    init {
       resetExampleCountdown()

        collectSpeedDrawCountdown()

        countDownJob?.invokeOnCompletion {
            println("SpeedDrawViewModel countdown finished. $it")
            navToResult()
        }
    }


    private fun collectSpeedDrawCountdown(){
        countDownJob = _speedDrawCountdown.startCountdown.onEach { count ->
            _topBarUiState.update {
                it.copy(
                    timeLeftSeconds = count
                )
            }
            if (count == 0){
                delay(100)
                countDownJob?.cancel()
            }
        }
            .launchIn(viewModelScope)
    }

    fun onDone(){
        _speedDrawCountdown.pause()

        viewModelScope.launch {
            val lastResult = ResultManager.INSTANCE.getLastResult() ?: return@launch
            val accuracy = resultCalculator.calculateResult(
                drawResult = lastResult,
                strokeWidthUser = 4
            ){
                bitmapPrinter.printBitmap(it, "speeddraw_${System.currentTimeMillis()}.png")
            }
            println("SpeedDrawViewModel accuracy: ${accuracy}")
            GamesManager.INSTANCE.updateLatestGame(GameMode.SPEED_DRAW, listOf(lastResult))

            val success = SpeedDrawIsSuccessUseCase().invoke(accuracy)
            val successCount = if(success) 1 else 0

            _topBarUiState.update {
                it.copy(
                    drawState = DrawState.EXAMPLE,
                    successes = it.successes + successCount
                )
            }
        }

        startNewExample()
        resetExampleCountdown()
    }

    private fun startNewExample(){
        exampleIndex.update {current ->
            val nextIndex = current + 1
            val update = if (nextIndex >= examples.size) 0 else nextIndex

            update
        }


        ResultManager.INSTANCE.getLastResult()?.let {result ->
            println("SPEED DRAW VIEWMODEL last result: $result")
            ResultManager.INSTANCE.updateResult(
                result.copy(
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