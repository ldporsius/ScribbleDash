package nl.codingwithlinda.scribbledash.core.domain.model


data class CoordinatesDrawResult(
    override val id: String,
    override val level: GameLevel,
    override val examplePath: List<DrawPath> = emptyList(),
    override val userPath: List<DrawPath> = emptyList()
): DrawResult


interface DrawResult{
    val id: String
    val level: GameLevel
    val examplePath: List<DrawPath>
    val userPath: List<DrawPath>
}


