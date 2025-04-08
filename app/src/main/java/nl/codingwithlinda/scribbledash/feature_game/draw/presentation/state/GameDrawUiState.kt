package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state

import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.feature_game.draw.data.ColoredDrawPath

data class GameDrawUiState(
    val drawPaths: List<ColoredDrawPath> = emptyList(),
    val undoStack: Int = 0,
    val redoStack: Int = 0,
    val currentPath: ColoredDrawPath? = null,
){
    fun isUndoAvailable() = undoStack > 0
    fun isRedoAvailable() = redoStack > 0
    fun isClearAvailable() = drawPaths.isNotEmpty()
}
