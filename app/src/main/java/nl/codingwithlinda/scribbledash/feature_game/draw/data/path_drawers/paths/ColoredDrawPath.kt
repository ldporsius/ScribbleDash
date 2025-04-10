package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths

import android.graphics.Path
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

data class ColoredDrawPath(
    val color: Color,
    override val path: Path
): AndroidDrawPath
