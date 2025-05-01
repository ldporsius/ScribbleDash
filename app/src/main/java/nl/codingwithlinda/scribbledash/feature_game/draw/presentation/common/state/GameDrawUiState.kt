package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state

import androidx.compose.runtime.Immutable
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.ColoredDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

@Immutable
data class GameDrawUiState(
    val drawPaths: List<AndroidDrawPath> = emptyList(),
    val canRedo: Boolean = false,
    val canUndo: Boolean = false,
    val currentPath: AndroidDrawPath? = null,
){
    fun isUndoAvailable() = canUndo
    fun isRedoAvailable() = canRedo
    fun isClearAvailable() = drawPaths.isNotEmpty() || canRedo


}
