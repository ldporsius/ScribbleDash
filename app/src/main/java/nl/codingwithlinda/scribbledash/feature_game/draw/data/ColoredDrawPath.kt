package nl.codingwithlinda.scribbledash.feature_game.draw.data

import android.graphics.Path
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

data class ColoredDrawPath(
    val color: Color,
    override val path: Path
): DrawPath
