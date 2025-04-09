package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state

import nl.codingwithlinda.scribbledash.feature_game.draw.data.ColoredDrawPath

data class GameDrawUiState(
    val drawPaths: List<ColoredDrawPath> = emptyList(),

    val redoStack: Int = 0,
    val currentPath: ColoredDrawPath? = null,
){
    fun isUndoAvailable() = drawPaths.isNotEmpty()
    fun isRedoAvailable() = redoStack > 0
    fun isClearAvailable() = drawPaths.isNotEmpty()
}
