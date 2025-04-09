package nl.codingwithlinda.scribbledash.feature_game.draw.data.memento

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker1
import nl.codingwithlinda.scribbledash.feature_game.draw.data.ColoredDrawPath

class GamePathCareTaker: CareTaker1<ColoredDrawPath> {
    private var mementos = listOf<ColoredDrawPath>()
    private var mementosUndone = mutableListOf<List<ColoredDrawPath>>()
    private var mementosRedone = listOf<ColoredDrawPath>()
    private val currentMementoIndex = MutableStateFlow(-1)

    private val _undoStack = currentMementoIndex.map{index ->
        mementos
    }
    private val _redoStack = currentMementoIndex.map { index ->

        mementosUndone.lastOrNull() ?: emptyList()

    }

    override fun save(memento: ColoredDrawPath) {
        mementos =
            mementos + memento

        currentMementoIndex.update {
            mementos.lastIndex
        }
    }
    override fun undo(): ColoredDrawPath? {

        val toRemove = mementos.lastOrNull()

        val canUndo = mementos.lastIndex > -1 && mementos.isNotEmpty()
        if (canUndo) {
            mementosUndone.add(mementos)
            mementos = mementos.dropLast(1)
        }

        currentMementoIndex.update {
            it.minus(1)
        }
        return toRemove
    }

    override fun redo(): ColoredDrawPath? {
        mementos = mementosUndone.lastOrNull() ?: mementos
        println("GAME CARETAKER. mementosUndone before redo ${mementosUndone.size}")

        val canRedo = mementosUndone.lastIndex > -1 && mementosUndone.isNotEmpty()

        if (canRedo)
          mementosUndone.removeAt(mementosUndone.lastIndex)

        println("GAME CARETAKER. mementosUndone after redo ${mementosUndone.size}")

        currentMementoIndex.update {
            it.plus(1)
        }

        val toRestore = mementos.lastOrNull()
        println("toRestore $toRestore")

        return toRestore
    }

    override fun clear() {

        currentMementoIndex.update {
            0
        }
    }


    override val redoStack: List<ColoredDrawPath>
        = mementosUndone.flatten()
}