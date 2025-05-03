package nl.codingwithlinda.scribbledash.core.domain.model

data class Game(
    val id: String,
    val dateCreated: Long,
    val gameMode: GameMode,
    val scores: List<Int>

)
