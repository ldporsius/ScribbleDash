package nl.codingwithlinda.scribbledash.core.test

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates

fun fakeDrawingOnCanvas(path: Path) = flow<Offset> {

    pathToCoordinates(path).forEach {
        emit(Offset(it.x, it.y))
        delay(10)
    }
}