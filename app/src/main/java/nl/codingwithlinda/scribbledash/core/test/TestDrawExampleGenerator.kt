package nl.codingwithlinda.scribbledash.core.test

import android.content.Context
import androidx.annotation.DrawableRes
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.parseVectorDrawable
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath

fun testExampleDrawableMultiPath(
    context: Context,
    @DrawableRes drawableResId: Int

): DrawPath{
    val xmlPathData = parseVectorDrawable(context, drawableResId)

    val parsedPaths = xmlPathData.map {pd ->
        androidx.core.graphics.PathParser.createPathFromPathData(pd.pathData)
    }.map{path ->

            pathToCoordinates(path)
    }
    return SimpleDrawPath(parsedPaths)
}
fun testExampleDrawable(
    context: Context,
    @DrawableRes drawableResId: Int

): DrawPath{
    val xmlPathData = parseVectorDrawable(context, drawableResId)

    val parsedPath = xmlPathData.map {pd ->
        androidx.core.graphics.PathParser.createPathFromPathData(pd.pathData)
    }.let {
        android.graphics.Path().apply {
            it.forEach { path ->
                addPath(path)
            }
        }
    }

    return SimpleDrawPath(
        paths = listOf( pathToCoordinates(parsedPath)),
    )
}