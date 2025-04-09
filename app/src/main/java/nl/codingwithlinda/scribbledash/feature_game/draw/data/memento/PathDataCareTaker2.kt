package nl.codingwithlinda.scribbledash.feature_game.draw.data.memento

import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.commands.DrawPathCommandImpl
import nl.codingwithlinda.scribbledash.feature_game.draw.data.commands.DrawPathCommandImpl2

class PathDataCareTaker2: CareTaker<PathData, List<PathData>> {
    private val pathData = mutableListOf<PathData>()
    private var mementos = listOf<DrawPathCommandImpl2>()
    private var cursor: Int = -1

    private fun mementosUndone() =  mementos.subList(cursor.plus(1).coerceIn(0 .. mementos.size), mementos.size)

    private fun canRedo() = cursor < mementos.lastIndex

    override fun save(memento: PathData) {
        pathData.add(memento)
        val mementoCopy = pathData.toList()
        val cmd = DrawPathCommandImpl2(mementoCopy)
        mementos = mementos.plusElement(cmd)
        cursor = mementos.lastIndex

        println("SAVE MEMENTOS: ${mementos.size}")
    }

    override fun undo(): List<PathData>{

        cursor =  cursor.minus(1).coerceAtLeast(-1)

        val toUndo = mementos.getOrNull(cursor)?.pathData ?: emptyList()
        pathData.clear()
        pathData.addAll(toUndo)
        println("UNDO CURSOR: $cursor")
        return toUndo
    }

    override fun redo(): List<PathData> {
        cursor = cursor.plus(1).coerceAtMost(mementos.lastIndex)

        val toRestore = mementos.getOrNull(cursor)?.pathData ?: emptyList()
        pathData.clear()
        pathData.addAll(toRestore)
        println("REDO CURSOR: $cursor")
        println("REDO MEMENTOS: ${mementos.size}")
        println("CAN REDO: ${canRedo()}")

        return toRestore
    }

    override fun clear() {
        mementos = emptyList()
        cursor = -1
    }

    override val redoStack: List<PathData>
        get() = mementosUndone().map {
            it.pathData
        }.flatten()
}