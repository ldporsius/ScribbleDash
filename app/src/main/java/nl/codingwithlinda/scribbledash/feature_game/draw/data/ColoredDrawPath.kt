package nl.codingwithlinda.scribbledash.feature_game.draw.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

data class ColoredDrawPath(
    val id: Int = 0,
    val color: Color,
    override val path: Path
): DrawPath
