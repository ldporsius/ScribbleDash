package nl.codingwithlinda.scribbledash.feature_game.draw.data

import androidx.compose.ui.geometry.Offset

data class PathData(
    val id: String,
    val color: Int,
    val path: List<Offset>

)
