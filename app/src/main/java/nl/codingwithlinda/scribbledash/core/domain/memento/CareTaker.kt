package nl.codingwithlinda.scribbledash.core.domain.memento


interface CareTaker<in T, out R> {
    fun save(memento: T)
    fun undo(): R?
    fun redo(): R?
    fun clear()
    fun canRedo(): Boolean
}