package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state


import androidx.compose.runtime.Immutable
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath

@Immutable
data class GameDrawUiState(
    val drawPaths: List<ColoredPath> = emptyList(),
    val canRedo: Boolean = false,
    val canUndo: Boolean = false,
    val currentPath: List<ColoredPath> = emptyList(),
){
    fun isUndoAvailable() = canUndo
    fun isRedoAvailable() = canRedo


}
