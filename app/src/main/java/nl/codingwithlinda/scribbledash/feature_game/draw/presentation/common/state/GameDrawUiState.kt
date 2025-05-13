package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state


import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Path

@Immutable
data class GameDrawUiState(
    val drawPaths: List<Path> = emptyList(),
    val canRedo: Boolean = false,
    val canUndo: Boolean = false,
    val currentPath: Path? = null,
){
    fun isUndoAvailable() = canUndo
    fun isRedoAvailable() = canRedo


}
