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
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.FinalResultUiState

class EndlessGameOverViewModel(
    private val gamesManager: GamesManager,
    private val ratingMapper: RatingMapper
): ViewModel() {

    private val _uiState = MutableStateFlow(FinalResultUiState())

    val uiState = _uiState
        .onStart {
            val avgAccuracy = gamesManager.averageAccuracyForLatestGame(GameMode.ENDLESS_MODE)
            val isTopScore = gamesManager.isNewTopScore(GameMode.ENDLESS_MODE)
            val numSuccesses = gamesManager.numberSuccessesForLatestGame(GameMode.ENDLESS_MODE)
            _uiState.update {
                it.copy(
                    ratingUi = ratingMapper.toUi(avgAccuracy),
                    isTopScore = isTopScore,
                    successCount = numSuccesses

                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    fun startNewGame(){
        viewModelScope.launch {
            gamesManager.addGame(GameMode.ENDLESS_MODE, emptyList())
        }
    }

}