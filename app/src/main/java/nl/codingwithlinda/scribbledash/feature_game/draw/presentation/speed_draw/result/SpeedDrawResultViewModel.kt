package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.FinalResultUiState

class SpeedDrawResultViewModel(
    private val ratingMapper: RatingMapper,
    private val gameEngine: GameEngineTemplate
): ViewModel() {

    private val _uiState = MutableStateFlow(FinalResultUiState())

    val uiState = _uiState
        .onStart {
            val avg = gameEngine.averageAccuracyForLatestGame()
            val ratingUi = ratingMapper.toUi(avg)

            val numSuccesses = gameEngine.numberSuccessesForLatestGame()
            val isTopScore = gameEngine.isNewTopScore()
            val isHighestNumberOfSuccesses = gameEngine.isHighestNumberOfSuccesses()

            _uiState.update {
                it.copy(
                    ratingUi = ratingUi,
                    successCount = numSuccesses,
                    isTopScore = isTopScore,
                    isHighestNumberOfSuccesses = isHighestNumberOfSuccesses
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


}