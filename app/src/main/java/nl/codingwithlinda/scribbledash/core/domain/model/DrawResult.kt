package nl.codingwithlinda.scribbledash.core.domain.model

import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

data class DrawResult(
    val id: String,
    val examplePath: AndroidDrawPath,
    val userPath: List<AndroidDrawPath> = emptyList()
)
