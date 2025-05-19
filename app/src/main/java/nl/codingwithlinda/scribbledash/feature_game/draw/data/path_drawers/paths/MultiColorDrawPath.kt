package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths

import nl.codingwithlinda.scribbledash.core.domain.model.MultiColorDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath

data class MultiColoredDrawPath(
    override val paths: List<ColoredPath>,
): MultiColorDrawPath
