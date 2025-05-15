package nl.codingwithlinda.scribbledash.e2e.util

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates

fun offsetPath(
    path: Path,
    xOffset: Float,
    yOffset: Float): List<Offset> {
    val result = path.let { path ->
        pathToCoordinates(path, 5f)
    }.map {
        Offset(it.x + xOffset, it.y + yOffset)
    }
    return result
}