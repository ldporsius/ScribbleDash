package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state

import android.graphics.Path
import androidx.compose.runtime.Immutable

@Immutable
data class GameDrawUiState(
    val drawPaths: List<Path> = emptyList(),
    val canRedo: Boolean = false,
    val canUndo: Boolean = false,
    val currentPath: Path? = null,
){
    fun isUndoAvailable() = canUndo
    fun isRedoAvailable() = canRedo
    fun isClearAvailable() = drawPaths.isNotEmpty() || canRedo


}
