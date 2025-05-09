package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths

import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.core.domain.model.SingleDrawPath

data class SimpleDrawPath(
    override val path : Path,
): SingleDrawPath
