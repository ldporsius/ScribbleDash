package nl.codingwithlinda.scribbledash.core.test

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.domain.model.CoordinatesDrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

fun fakeDrawResultSamePaths(): DrawResult {
    return CoordinatesDrawResult(
        id = System.currentTimeMillis().toString(),
        level = GameLevel.BEGINNER,
        examplePath = listOf( fakeAndroidDrawPathSquare()),
        userPath = listOf( fakeAndroidDrawPathSquare()),
    )
}

fun fakeDrawResultDifferentPaths(): CoordinatesDrawResult{
    return CoordinatesDrawResult(
        id = System.currentTimeMillis().toString(),
        level = GameLevel.BEGINNER,
        examplePath = listOf( fakeAndroidDrawPathSquare()),
        userPath = listOf( fakeAndroidDrawPathCircle()),
    )
}


fun fakeAndroidDrawPathSquare(): DrawPath {
    return SimpleDrawPath(
        paths = listOf( pathToCoordinates( fakePathSquare()))
    )
}
fun fakeAndroidDrawPathCircle(): DrawPath{
    return SimpleDrawPath(
        paths = listOf( pathToCoordinates(fakePathCircle())),
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
        color = 0,
        path = fakeOffsets()
    )
}
fun fakeOffsets() = List(10){
    Offset(it.toFloat(), it.toFloat())
}