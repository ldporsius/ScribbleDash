package nl.codingwithlinda.scribbledash.feature_game.draw.data

import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker

class GamePathsCareTaker: CareTaker<List<ColoredDrawPath>> {
    private var mementos = listOf<List<ColoredDrawPath>>()
    private var mementosUndone = listOf<List<ColoredDrawPath>>()

    override fun save(memento: List<ColoredDrawPath>) {
        println("CARETAKER SAVES MEMENTO: $memento")
       val newList = mementos.toMutableList().apply {
           this.add(memento)
       }
        mementos = newList
        println("CARETAKER HAS LIST OF MEMENTOS: ${mementos}")
        println("CARETAKER HAS LIST OF MEMENTOS SIZE: ${mementos.size}")

    }

    override fun undo(): List<ColoredDrawPath> {
        println("CARETAKER UNDOS MEMENTO: ${mementos.lastOrNull()}")

        val undoMemento = mementos.lastOrNull() ?: return emptyList()
        val newList = mementosUndone.toMutableList().apply {
            this.add(undoMemento)
        }
        mementosUndone = newList
        mementos = mementos.dropLast(1)

        println("CARETAKER HAS LIST OF UNDOS: ${mementosUndone}")
        return mementos.lastOrNull() ?: emptyList()
    }

    override fun redo(): List<ColoredDrawPath>? {
        println("--- in redo ----")
        println("CARETAKER HAS LIST OF UNDOS: ${mementosUndone}")
        println("CARETAKER REDO MEMENTO: ${mementosUndone.lastOrNull()}")

        val redoMemento = mementosUndone.lastOrNull() ?: return null
        save(redoMemento)
        mementosUndone = mementosUndone.dropLast(1)

        println("CARETAKER HAS REDO: ${redoMemento}")
        return redoMemento
    }

    override fun clear() {
        println("CARETAKER CLEARS MEMENTOS")
        mementos = emptyList()

    }

    override val redoStack: List<List<ColoredDrawPath>>
        get() = mementosUndone
}