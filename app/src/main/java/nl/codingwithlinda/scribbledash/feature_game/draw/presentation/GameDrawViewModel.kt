package nl.codingwithlinda.scribbledash.feature_game.draw.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.GameDrawUiState

class GameDrawViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameDrawUiState())
    val uiState = _uiState.asStateFlow()

    fun handleAction(action: DrawAction){
        when(action){
            is DrawAction.Draw -> {
                _uiState.value = _uiState.value.copy(
                    currentOffset = action.offset
                )
            }
            DrawAction.Clear -> {
                _uiState.value = GameDrawUiState()
            }
            DrawAction.Redo -> {

            }
            DrawAction.Save -> {

            }
            DrawAction.Undo -> {

            }
        }
    }
}