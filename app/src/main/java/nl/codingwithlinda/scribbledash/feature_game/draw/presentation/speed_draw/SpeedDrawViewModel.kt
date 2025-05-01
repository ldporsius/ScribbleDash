package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.state.DrawExampleUiState

class SpeedDrawViewModel: ViewModel() {

    private val _topBarUiState = MutableStateFlow(SpeedDrawUiState())
    val topBarUiState = _topBarUiState.asStateFlow()


    private val _exampleUiState = MutableStateFlow(DrawExampleUiState())
    val exampleUiState = _exampleUiState.asStateFlow()


    fun handleAction(action: DrawAction){

    }
}