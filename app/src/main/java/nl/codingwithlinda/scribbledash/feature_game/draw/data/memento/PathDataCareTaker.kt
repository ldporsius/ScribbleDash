package nl.codingwithlinda.scribbledash.feature_game.draw.data.memento

import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.commands.DrawPathCommandImpl

class PathDataCareTaker: CareTaker<PathData, List<PathData>> {
    private var mementos = listOf<DrawPathCommandImpl>()
    //private var cursor: Int = 0
    private fun mementosActive() = mementos.filter {
        !it.isUndone
    }
    private fun mementosUndone() =  mementos.takeLastWhile {
        it.isUndone
    }

//    private fun lastActiveCmd(): DrawPathCommandImpl? = mementos.getOrNull(cursor)
    private fun lastActiveCmd(): DrawPathCommandImpl? = mementosActive().lastOrNull()
    private fun lastUndoneCmd(): DrawPathCommandImpl? = mementosUndone().lastOrNull()
    private fun firstUndoneCmd(): DrawPathCommandImpl? = mementosUndone().firstOrNull()

    override fun canRedo() = mementosUndone().isNotEmpty()

    override fun save(memento: PathData) {
        val cmd = DrawPathCommandImpl(memento)
        //reset mementos to clear history
        val current = mementosActive()

        mementos = current.plusElement(cmd).takeLast(5)
        //cursor = mementos.size

        println("SAVE MEMENTOS Size: ${mementos.size}")
        println("SAVE MEMENTOS UNDONE Size: ${mementosUndone().size}")
        println("SAVE MEMENTOS ACTIVE Size: ${mementosActive().size}")
        //println("SAVE MEMENTOS CURSOR: $cursor")
    }

    override fun undo(): List<PathData>{
        mementosActive().lastOrNull()?.undo()
        //val cursorPosition = cursor.minus(1)
        //cursor = cursorPosition.coerceAtLeast(-1)

        //println("UNDO CURSOR: $cursor")
        return mementosActive().map {
            it.pathData
        }
    }

    override fun redo(): List<PathData> {

        //println("REDO CURSOR: $cursor")
        println(" in REDO, Undone MEMENTOS: ${mementosUndone()}")
        println(" in REDO, MEMENTO THAT GETS REDONE: ${lastUndoneCmd()}")
        println("CAN REDO: ${canRedo()}")

        firstUndoneCmd()?.execute()
        //cursor = cursor.plus(1).coerceAtMost(mementos.lastIndex)

        return mementosActive().map {
            it.pathData
        }
    }

    override fun clear() {
        mementos = emptyList()
        //cursor = -1
    }

}