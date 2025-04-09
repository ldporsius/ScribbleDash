package nl.codingwithlinda.scribbledash.core.domain.memento


interface CareTaker1<T> {
    fun save(memento: T)
    fun undo(): T?
    fun redo(): T?
    fun clear()

    val redoStack: List<T>
}