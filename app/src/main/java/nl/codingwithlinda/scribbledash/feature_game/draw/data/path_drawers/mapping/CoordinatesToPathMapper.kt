package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping

import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.domain.model.PathCoordinates

fun coordinatesToPath(coordinates: List<List<PathCoordinates>>): Path{
    val pathUnion = Path()
    coordinates.onEach {coors ->
        val path = Path()
        path.moveTo(coors.first().x, coors.first().y)

        (1 .. coors.lastIndex).onEach {
            path.lineTo(coors[it].x, coors[it].y)
        }

        pathUnion.addPath(path)

    }

   return pathUnion
}