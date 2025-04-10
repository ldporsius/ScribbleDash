package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths

import android.graphics.Path
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

data class SimpleDrawPath(
    override val path: Path,
): AndroidDrawPath
