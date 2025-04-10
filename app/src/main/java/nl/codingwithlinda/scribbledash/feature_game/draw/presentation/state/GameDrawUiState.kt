package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state

import androidx.compose.runtime.Immutable
import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.ColoredDrawPath

@Immutable
data class GameDrawUiState(
    val drawPaths: List<PathData> = emptyList(),
    val canRedo: Boolean = false,
    val currentPath: PathData? = null,
){
    fun isUndoAvailable() = drawPaths.isNotEmpty()
    fun isRedoAvailable(): Boolean{
        println("CAN REDO in GAME DRAW UISTATE: $canRedo")
        return canRedo
    }
    fun isClearAvailable() = drawPaths.isNotEmpty() || canRedo
}
