package nl.codingwithlinda.scribbledash.core.test

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

fun fakeDrawResultSamePaths(): DrawResult {
    return DrawResult(
        id = System.currentTimeMillis().toString(),
        level = GameLevel.BEGINNER,
        examplePath = listOf( fakePathSquare()),
        userPath = listOf( fakePathSquare()),
    )
}

fun fakeDrawResultDifferentPaths(): DrawResult{
    return DrawResult(
        id = System.currentTimeMillis().toString(),
        level = GameLevel.BEGINNER,
        examplePath = listOf( fakePathSquare()),
        userPath = listOf( fakePathCircle()),
    )
}

fun Path.toColoredPath(color: Int = android.graphics.Color.BLACK) = ColoredPath(
    path = this,
    color = color
)
fun fakeAndroidDrawPathSquare(): DrawPath {
    return SimpleDrawPath(
        path = fakePathSquare()
    )
}
fun fakeAndroidDrawPathCircle(): DrawPath{
    return SimpleDrawPath(
        path = fakePathCircle(),
    )
}
fun fakePathSquare(): Path{
    return Path().apply {
       addRect(0f, 0f, 100f, 100f, Path.Direction.CW)
    }
}
fun fakePathCircle(): Path{
    return Path().apply {
        this.addCircle(
            0f, 0f, 100f, Path.Direction.CW
        )
    }
}
fun fakePathData() = List(10){
    PathData(
        id = it.toString(),
        path = fakeOffsets()
    )
}
fun fakeOffsets() = List(10){
    Offset(it.toFloat(), it.toFloat())
}