package nl.codingwithlinda.scribbledash.core.test

import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

fun fakeDrawResultSamePaths(): DrawResult{
    return DrawResult(
        id = System.currentTimeMillis().toString(),
        level = GameLevel.BEGINNER,
        examplePath = listOf( fakeAndroidDrawPathSquare()),
        userPath = listOf( fakeAndroidDrawPathSquare()),
    )
}

fun fakeDrawResultDifferentPaths(): DrawResult{
    return DrawResult(
        id = System.currentTimeMillis().toString(),
        level = GameLevel.BEGINNER,
        examplePath = listOf( fakeAndroidDrawPathSquare()),
        userPath = listOf( fakeAndroidDrawPathCircle()),
    )
}


fun fakeAndroidDrawPathSquare(): AndroidDrawPath{
    return SimpleDrawPath(
        path = fakePathSquare()
    )
}
fun fakeAndroidDrawPathCircle(): AndroidDrawPath{
    return SimpleDrawPath(
        path = fakePathCircle()
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