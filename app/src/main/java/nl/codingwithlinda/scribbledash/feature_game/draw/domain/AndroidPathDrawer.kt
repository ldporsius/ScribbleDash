package nl.codingwithlinda.scribbledash.feature_game.draw.domain

import androidx.compose.ui.geometry.Offset

interface AndroidPathDrawer: PathDrawer<AndroidDrawPath> {
    override fun drawPath(path: List<Offset>, color: Int): AndroidDrawPath
}