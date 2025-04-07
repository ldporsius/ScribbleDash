package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state

import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

data class GameDrawUiState(
    val drawPaths: List<DrawPath> = emptyList(),
    val undoStack: List<DrawPath> = emptyList(),
    val redoStack: List<DrawPath> = emptyList(),
    val currentPath: DrawPath? = null,
    val currentOffset: Offset? = null
){
    fun isUndoAvailable() = undoStack.isNotEmpty()
    fun isRedoAvailable() = redoStack.isNotEmpty()
    fun isClearAvailable() = drawPaths.isNotEmpty()
}
