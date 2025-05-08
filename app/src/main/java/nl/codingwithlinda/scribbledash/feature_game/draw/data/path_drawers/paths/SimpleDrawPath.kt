package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths

import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.core.domain.model.PathCoordinates
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping.coordinatesToPath

data class SimpleDrawPath(
    override val paths: List<List<PathCoordinates>>,
): DrawPath {
   val androidPath: Path = coordinatesToPath(paths)
}
