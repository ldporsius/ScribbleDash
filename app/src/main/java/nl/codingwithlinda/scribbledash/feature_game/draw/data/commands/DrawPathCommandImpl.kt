package nl.codingwithlinda.scribbledash.feature_game.draw.data.commands

import nl.codingwithlinda.scribbledash.core.domain.commands.DrawPathCommand
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

data class DrawPathCommandImpl(
    val pathData: PathData,
): DrawPathCommand  {
    override var isUndone = false
    override fun execute() {
        isUndone = false
    }

    override fun undo() {
       isUndone = true
    }
}