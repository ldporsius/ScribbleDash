package nl.codingwithlinda.scribbledash.core.domain.model

import android.graphics.Path


data class DrawResult(
    val id: String,
    val level: GameLevel,
    val examplePath: List<Path> = emptyList(),
    val userPath: List<Path> = emptyList()
)


