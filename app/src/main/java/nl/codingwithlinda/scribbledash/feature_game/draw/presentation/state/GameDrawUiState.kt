package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state

import androidx.compose.runtime.Immutable
import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.ColoredDrawPath

@Immutable
data class GameDrawUiState(
    val drawState: DrawState = DrawState.EXAMPLE,
    val drawPaths: List<PathData> = emptyList(),
    val canRedo: Boolean = false,
    val canUndo: Boolean = false,
    val currentPath: PathData? = null,
){
    fun isUndoAvailable() = canUndo
    fun isRedoAvailable(): Boolean{
        println("CAN REDO in GAME DRAW UISTATE: $canRedo")
        return canRedo
    }
    fun isClearAvailable() = drawPaths.isNotEmpty() || canRedo


}
