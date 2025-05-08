package nl.codingwithlinda.scribbledash.feature_game.draw.domain

import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.core.domain.model.PathCoordinates

interface CoordinatesDrawPath: DrawPath {
    override val paths: List<List<PathCoordinates>>
}