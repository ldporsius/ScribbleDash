package nl.codingwithlinda.scribbledash.feature_game.draw.domain

import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

interface PathCreator<T: DrawPath> {
    fun drawPath(path: List<Offset>): T
}