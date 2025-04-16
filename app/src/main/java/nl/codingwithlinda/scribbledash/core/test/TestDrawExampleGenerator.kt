package nl.codingwithlinda.scribbledash.core.test

import android.content.Context
import androidx.annotation.DrawableRes
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.parseVectorDrawable
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

fun testExampleDrawable(
    context: Context,
    @DrawableRes drawableResId: Int

): AndroidDrawPath{
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
        path = parsedPath
    )
}