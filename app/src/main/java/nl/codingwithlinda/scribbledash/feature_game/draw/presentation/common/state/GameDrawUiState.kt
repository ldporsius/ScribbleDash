package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state

import androidx.compose.runtime.Immutable
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

@Immutable
data class GameDrawUiState(
    val drawPaths: List<PathData> = emptyList(),
    val canRedo: Boolean = false,
    val canUndo: Boolean = false,
    val currentPath: PathData? = null,
){
    fun isUndoAvailable() = canUndo
    fun isRedoAvailable() = canRedo
    fun isClearAvailable() = drawPaths.isNotEmpty() || canRedo


}
