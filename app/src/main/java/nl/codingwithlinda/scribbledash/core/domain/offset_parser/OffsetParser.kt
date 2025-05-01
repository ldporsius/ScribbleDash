package nl.codingwithlinda.scribbledash.core.domain.offset_parser

import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer

interface OffsetParser<T: DrawPath> {
    fun parseOffset(
        pathDrawer: PathDrawer<T>,
        pathData: PathData
    ): T
}