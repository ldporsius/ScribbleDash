package nl.codingwithlinda.scribbledash.core.domain.model

data class Game(
    val id: String,
    val gameMode: GameMode,
    val drawResults: List<DrawResult>

)
