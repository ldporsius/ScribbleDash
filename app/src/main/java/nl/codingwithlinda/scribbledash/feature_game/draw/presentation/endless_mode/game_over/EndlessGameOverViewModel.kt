package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.game_over

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
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.EndlessGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.FinalResultUiState

class EndlessGameOverViewModel(
    private val gameEngine: GameEngineTemplate,
    private val ratingMapper: RatingMapper
): ViewModel() {

    private val _uiState = MutableStateFlow(FinalResultUiState())

    val uiState = _uiState
        .onStart {
            val avgAccuracy = gameEngine.averageAccuracyForLatestGame()
            val isTopScore = gameEngine.isNewTopScore()
            val numSuccesses = gameEngine.numberSuccessesForLatestGame()
            val ratingUi = ratingMapper.toUi(avgAccuracy)
            val isHighestNumberOfSuccesses = true
            _uiState.update {
                it.copy(
                    ratingUi = ratingUi,
                    isTopScore = isTopScore,
                    successCount = numSuccesses,
                    isHighestNumberOfSuccesses = isHighestNumberOfSuccesses

                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


}