package nl.codingwithlinda.scribbledash.core.domain.offset_parser

import nl.codingwithlinda.scribbledash.core.domain.model.PathCoordinates
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

interface OffsetParser{
    fun parseOffset(
        pathData: PathData
    ): List<PathCoordinates>
}