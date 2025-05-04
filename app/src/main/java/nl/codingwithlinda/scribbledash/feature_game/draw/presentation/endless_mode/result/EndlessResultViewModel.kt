package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.combinedPath
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper

class EndlessResultViewModel(
    private val gamesManager: GamesManager,
    private val ratingMapper: RatingMapper
): ViewModel() {

    private val _uiState = MutableStateFlow(EndlessResultUiState())

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val successes = gamesManager.numberSuccessesForLatestGame(GameMode.ENDLESS_MODE)
            _uiState.update {
                it.copy(
                    numberSuccess = successes
                )
            }

            ResultManager.INSTANCE.getLastResult()?.let { result ->
                val accuracy = ResultCalculator.calculateResult(result, 4)

                val ratingUi = ratingMapper.toUi(accuracy)
                val isSuccessful = accuracy >= RatingFactory.getSuccessLimit(GameMode.ENDLESS_MODE)
                _uiState.update {
                    it.copy(
                        ratingUi = ratingUi,
                        examplePath = combinedPath(result.examplePath.map { it.path }),
                        userPath = result.userPath,
                        isSuccessful = isSuccessful
                    )
                }
            }
        }
    }
}