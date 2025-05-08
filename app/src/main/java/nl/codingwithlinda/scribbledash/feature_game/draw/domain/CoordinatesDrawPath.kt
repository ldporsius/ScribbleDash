package nl.codingwithlinda.scribbledash.feature_game.draw.domain

import nl.codingwithlinda.scribbledash.core.data.draw_examples.PathCoordinates
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

interface CoordinatesDrawPath: DrawPath {
    override val path: List<PathCoordinates>
}