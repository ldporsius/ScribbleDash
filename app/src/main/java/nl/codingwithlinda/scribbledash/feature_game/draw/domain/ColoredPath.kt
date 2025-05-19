package nl.codingwithlinda.scribbledash.feature_game.draw.domain

import android.graphics.Path
import androidx.compose.ui.graphics.Color

data class ColoredPath(
    val path: Path,
    val color: Color
)
