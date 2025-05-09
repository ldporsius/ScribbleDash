package nl.codingwithlinda.scribbledash.core.test

import android.content.Context
import androidx.annotation.DrawableRes
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.parseVectorDrawable
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExample

fun testExampleDrawableMultiPath(
    context: Context,
    @DrawableRes drawableResId: Int

): DrawExample{
    val xmlPathData = parseVectorDrawable(context, drawableResId)

    val parsedPaths = xmlPathData.map {pd ->
        androidx.core.graphics.PathParser.createPathFromPathData(pd.pathData)
    }
    return DrawExample(parsedPaths)
}
fun testExampleDrawable(
    context: Context,
    @DrawableRes drawableResId: Int

): DrawExample{
    val xmlPathData = parseVectorDrawable(context, drawableResId)

    val parsedPath = xmlPathData.map {pd ->
        androidx.core.graphics.PathParser.createPathFromPathData(pd.pathData)
    }

    return DrawExample(parsedPath)
}