package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.state

import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState

data class EndlessUiState(
    val drawState: DrawState = DrawState.EXAMPLE,
    val numberSuccess: Int = 0,
)
