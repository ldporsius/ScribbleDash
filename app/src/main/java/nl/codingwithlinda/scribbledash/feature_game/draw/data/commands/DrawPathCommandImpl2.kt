package nl.codingwithlinda.scribbledash.feature_game.draw.data.commands

import nl.codingwithlinda.scribbledash.core.domain.commands.DrawPathCommand
import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData

data class DrawPathCommandImpl2(
    val pathData: List<PathData>,
): DrawPathCommand  {
    override var isUndone = false
    override fun execute() {
        isUndone = false
    }

    override fun undo() {
       isUndone = true
    }
}