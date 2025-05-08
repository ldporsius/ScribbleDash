package nl.codingwithlinda.scribbledash.core.domain.model

import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.CoordinatesDrawPath

data class AndroidDrawResult(
    override val id: String,
    override val level: GameLevel,
    override val examplePath: List<AndroidDrawPath> = emptyList(),
    override val userPath: List<AndroidDrawPath> = emptyList()
): DrawResult

data class CoordinatesDrawResult(
    override val id: String,
    override val level: GameLevel,
    override val examplePath: List<CoordinatesDrawPath> = emptyList(),
    override val userPath: List<AndroidDrawPath> = emptyList()
): DrawResult


interface DrawResult{
    val id: String
    val level: GameLevel
    val examplePath: List<DrawPath>
    val userPath: List<DrawPath>
}

inline fun <reified T: DrawResult> createTypeSafeDrawResult(result: DrawResult): T{
    return result as T
}

inline fun <reified T: DrawPath> createTypeSafeDrawPath(path: DrawPath): T{
    return path as T
}
