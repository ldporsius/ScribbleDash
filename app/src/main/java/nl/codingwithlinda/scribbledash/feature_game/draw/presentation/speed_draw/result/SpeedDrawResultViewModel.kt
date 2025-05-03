package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper

class SpeedDrawResultViewModel(
    private val ratingMapper: RatingMapper,
    private val gamesManager: GamesManager
): ViewModel() {

    private val _uiState = MutableStateFlow(SpeedDrawResultUiState())

    val uiState = _uiState
        .onStart {
            val avg = gamesManager.averageAccuracyForLatestGame(GameMode.SPEED_DRAW)
            val rating = RatingFactory.getRating(avg)
            val ratingUi = ratingMapper.toUi(rating, avg)

            val numSuccesses = gamesManager.numberSuccessesForLatestGame(GameMode.SPEED_DRAW)
            val isTopScore = gamesManager.isNewTopScore(GameMode.SPEED_DRAW)
            val isHighestNumberOfSuccesses = gamesManager.isHighestNumberOfSuccesses(GameMode.SPEED_DRAW)

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