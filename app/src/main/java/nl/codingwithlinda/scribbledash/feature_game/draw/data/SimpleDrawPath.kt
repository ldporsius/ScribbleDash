package nl.codingwithlinda.scribbledash.feature_game.draw.data

import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

data class SimpleDrawPath(
    override val path: Path,
): DrawPath
