package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.core.domain.model.MultiColorDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.MultiColoredDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathCreator

class MultiColorPathCreator(
    private val colors: List<Color>
): PathCreator<MultiColorDrawPath> {
    override fun drawPath(path: List<Offset>): MultiColorDrawPath {
        if (path.isEmpty()) return MultiColoredDrawPath(emptyList())

        val paths = mutableListOf<ColoredPath>()

        val chunkSize = (colors.size).coerceAtLeast(1)
        path.chunked( chunkSize ).mapIndexed { index, offsets ->

            val colorIndex = index % colors.size
            val color = colors.getOrElse(colorIndex, {Color.Transparent})

            val subpath = Path()

            subpath.moveTo(offsets.first().x, offsets.first().y)
            offsets.onEach { offset ->
                subpath.lineTo(offset.x, offset.y)
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