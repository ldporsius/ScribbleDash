package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state

import androidx.compose.ui.geometry.Offset

sealed interface DrawAction {
    data class Draw(val offset: Offset) : DrawAction
    object Undo : DrawAction
    object Redo : DrawAction
    object Clear : DrawAction
    object Save : DrawAction
}