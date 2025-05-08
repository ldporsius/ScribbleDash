package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths

import nl.codingwithlinda.scribbledash.core.data.draw_examples.PathCoordinates
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.CoordinatesDrawPath

data class SimpleCoordinatesDrawPath(override val path: List<PathCoordinates>): CoordinatesDrawPath
