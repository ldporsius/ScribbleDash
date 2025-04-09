package nl.codingwithlinda.scribbledash.core.domain.commands

interface DrawPathCommand {
    var isUndone: Boolean
    fun execute()
    fun undo()
}