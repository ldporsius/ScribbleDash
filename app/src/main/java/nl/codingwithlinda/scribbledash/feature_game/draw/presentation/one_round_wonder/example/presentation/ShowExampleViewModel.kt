package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping.coordinatesToPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.state.DrawExampleUiState

class ShowExampleViewModel(
    private val gameEngine: GameEngine,
    navToDraw: () -> Unit
): ViewModel() {

    private val _uiState = MutableStateFlow(DrawExampleUiState())
    val uiState = _uiState.combine(gameEngine.countDown){
        state, time ->
        state.copy(
            counter = time
        )

    }.onEach {
        if (it.counter == 0){
            navToDraw()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {

        val example = gameEngine.provideExample().examplePath.map {
            coordinatesToPath(it.path)
        }

            _uiState.update {
                it.copy(
                    drawPaths = example
                )
            }

        }
}