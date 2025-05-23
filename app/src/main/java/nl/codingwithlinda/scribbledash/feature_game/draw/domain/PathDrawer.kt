package nl.codingwithlinda.scribbledash.feature_game.draw.domain

import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

interface PathDrawer<T: DrawPath> {
    fun drawPath(path: List<Offset>, color: Int): T
}