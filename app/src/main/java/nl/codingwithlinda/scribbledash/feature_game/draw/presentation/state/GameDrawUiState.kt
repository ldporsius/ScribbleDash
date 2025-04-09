package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state

import androidx.compose.runtime.Immutable
import nl.codingwithlinda.scribbledash.feature_game.draw.data.ColoredDrawPath

@Immutable
data class GameDrawUiState(
    val drawPaths: List<ColoredDrawPath> = emptyList(),
    val canRedo: Boolean = false,
    val currentPath: ColoredDrawPath? = null,
){
    fun isUndoAvailable() = drawPaths.isNotEmpty()
    fun isRedoAvailable(): Boolean{
        println("CAN REDO in GAME DRAW UISTATE: $canRedo")
        return canRedo
    }
    fun isClearAvailable() = drawPaths.isNotEmpty() || canRedo
}
