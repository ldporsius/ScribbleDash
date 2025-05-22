package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.core.domain.model.MultiColorDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.MultiColoredDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathCreator

class MultiColorPathCreator(
    private val colors: List<Int>
): PathCreator<MultiColorDrawPath> {
    override fun drawPath(path: List<Offset>): MultiColorDrawPath {
        if (path.isEmpty()) {
            println("MULTICOLORPATHCREATOR PATH IS EMPTY")
            return MultiColoredDrawPath(emptyList())
        }

        val paths = mutableListOf<ColoredPath>()

        val chunkSize = (colors.size).coerceAtLeast(10)
        path.chunked( chunkSize ).mapIndexed { index, offsets ->

            val colorIndex = index % colors.size
            val color = colors.getOrElse(colorIndex, {android.graphics.Color.BLACK})

            val previousOffset = path.getOrElse(index * chunkSize - 1, { path.first()})

            val subpath = Path()

            subpath.moveTo(previousOffset.x, previousOffset.y)

            (0 until   offsets.size).onEach { i ->
                subpath.lineTo(offsets[i].x, offsets[i].y)
            }
            paths.add(
               ColoredPath(
                   path = subpath,
                   color = color
               )
            )

        }
        return MultiColoredDrawPath(
            paths = paths
        )
    }
}