package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths

import nl.codingwithlinda.scribbledash.core.domain.model.PathCoordinates
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.CoordinatesDrawPath

data class SimpleCoordinatesDrawPath(
    override val paths: List<List<PathCoordinates>>
): CoordinatesDrawPath
