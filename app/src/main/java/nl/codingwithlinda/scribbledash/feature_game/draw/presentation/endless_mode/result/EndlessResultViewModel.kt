package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class EndlessResultViewModel(
    private val gameEngine: GameEngineTemplate,
    private val ratingMapper: RatingMapper,
    private val bmPrinter: AndroidBitmapPrinter
): ViewModel() {

    private val _uiState = MutableStateFlow(EndlessResultUiState())

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val successes = gameEngine.numberSuccessesForLatestGame()
            val accuracy = gameEngine.getAccuracy()
            val ratingUi = ratingMapper.toUi(accuracy)

            val isSuccessful = gameEngine.isGameSuccessful()


            _uiState.update {
                it.copy(
                    numberSuccess = successes,
                    ratingUi = ratingUi,
                    isSuccessful = isSuccessful
                )
            }

            gameEngine.getResult().let { result ->

                ResultCalculator.calculateResult(result, 4){
                    bmPrinter.printBitmap(it,"endless_bm_${System.currentTimeMillis()}.png")
                }
                _uiState.update {
                    it.copy(
                        examplePath = combinedPath(result.examplePath),
                        userPath = result.userPath,
                    )
                }
            }
        }
    }
}