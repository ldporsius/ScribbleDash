package nl.codingwithlinda.scribbledash.core.domain.model

import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

data class DrawResult(
    val id: String,
    val level: GameLevel,
    val examplePath: AndroidDrawPath = SimpleDrawPath(),
    val userPath: List<AndroidDrawPath> = emptyList()
)
