package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state

import androidx.compose.ui.geometry.Offset

sealed interface DrawAction {
    data class StartPath(val offset: Offset) : DrawAction
    data class Draw(val offset: Offset) : DrawAction
    data object Undo : DrawAction
    data object Redo : DrawAction
    data object Clear : DrawAction
    data object Save : DrawAction
    data object Done : DrawAction
}