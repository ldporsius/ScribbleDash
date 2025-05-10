package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.state.EndlessUiState

class EndlessDrawViewModel(
    private val gameEngine: GameEngineTemplate
): ViewModel() {

    private val _endlessUiState = MutableStateFlow(EndlessUiState())
    val endlessUiState = _endlessUiState.asStateFlow()

    init {
       startNewGame()
    }

    private fun startNewGame(){
        viewModelScope.launch {
            val numberSuccesses = gameEngine.numberSuccessesForLatestGame()

            _endlessUiState.update {
                it.copy(
                    numberSuccess = numberSuccesses
                )
            }
        }
    }
}