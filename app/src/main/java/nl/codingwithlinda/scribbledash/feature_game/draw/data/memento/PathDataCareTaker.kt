package nl.codingwithlinda.scribbledash.feature_game.draw.data.memento

import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.commands.DrawPathCommandImpl

class PathDataCareTaker: CareTaker<PathData, List<PathData>> {
    private var mementos = listOf<DrawPathCommandImpl>()
    private var cursor: Int = -1
    private fun mementosActive() = mementos.filter {
        !it.isUndone
    }
    private fun mementosUndone() =  mementos.takeLastWhile {
        it.isUndone
    }.map {
        it.pathData
    }

    private fun lastActiveCmd(): DrawPathCommandImpl? = mementos.getOrNull(cursor)

    private fun canRedo() = mementos.getOrNull(cursor)?.isUndone == true

    override fun save(memento: PathData) {
        val cmd = DrawPathCommandImpl(memento)
        mementos = mementos.plusElement(cmd)
        cursor = mementos.lastIndex
    }

    override fun undo(): List<PathData>{
        mementosActive().lastOrNull()?.undo()
        val cursorPosition = mementosActive().lastIndex
        cursor = cursorPosition.coerceAtLeast(-1)

        println("UNDO CURSOR: $cursor")
        return mementosActive().map {
            it.pathData
        }
    }

    override fun redo(): List<PathData> {
        cursor = cursor.plus(1).coerceAtMost(mementos.lastIndex)

        println("REDO CURSOR: $cursor")
        println("REDO MEMENTOS: ${mementos.size}")
        println("CAN REDO: ${canRedo()}")
        if (canRedo()) {
          lastActiveCmd()?.execute()
        }
        return mementosActive().map {
            it.pathData
        }
    }

    override fun clear() {
        mementos = emptyList()
        cursor = -1
    }

    override val redoStack: List<PathData>
        get() = mementosUndone()
}