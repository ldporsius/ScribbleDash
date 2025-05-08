package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping

import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.data.draw_examples.PathCoordinates
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

fun coordinatesToPath(coordinates: List<PathCoordinates>): AndroidDrawPath{
    val path = Path()
    path.moveTo(coordinates.first().x, coordinates.first().y)
    coordinates.onEach {
        path.lineTo(it.x, it.y)
    }

    return SimpleDrawPath(
        path = path
    )
}