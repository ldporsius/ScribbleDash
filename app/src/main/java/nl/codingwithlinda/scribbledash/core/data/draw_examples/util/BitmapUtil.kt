package nl.codingwithlinda.scribbledash.core.data.draw_examples.util

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

fun normalisedPath(
    path: android.graphics.Path,
    requiredSize: Int,
    inset: Float,
): android.graphics.Path{

    val rect = RectF()
    path.computeBounds(rect, true)

    println("--In normalisedPath fun--. requiredSize = $requiredSize")
    println("--In normalisedPath fun--. rect: w = ${rect.width()},h = ${rect.height()}")
    println("--In normalisedPath fun--. rect: left = ${rect.left},top = ${rect.top}, right = ${rect.right}, bottom = ${rect.bottom}")
    println("--In normalisedPath fun--. rect: centerX = ${rect.centerX()}, centerY = ${rect.centerY()}")

    //println("--In normalisedPath fun--. inset = $inset")

    val sx = requiredSize.toFloat() / (rect.width() )
    val sy = requiredSize.toFloat() / (rect.height())
    val scaleMin = minOf(sx, sy)
    val aspectRatio = rect.width() / rect.height()

    println("--In normalisedPath fun--. sx = $sx, sy = $sy, scaleMin = $scaleMin")

    val dx = requiredSize.toFloat() / 2 - rect.centerX()
    val dy = requiredSize.toFloat() / 2 - rect.centerY()

    println("--In normalisedPath fun--. dx = $dx, dy = $dy")

    path.transform(Matrix().apply {
        setTranslate(-rect.left, -rect.top)
    }
    )
    path.computeBounds(rect, true)
    println("--In normalisedPath fun--. rect 2: w = ${rect.width()},h = ${rect.height()}")
    println("--In normalisedPath fun--. rect 2: left = ${rect.left},top = ${rect.top}, right = ${rect.right}, bottom = ${rect.bottom}")
    println("--In normalisedPath fun--. rect 2: centerX = ${rect.centerX()}, centerY = ${rect.centerY()}")

    val dstRect = RectF(
        inset,
        inset,
        requiredSize + inset,
        rect.height() * scaleMin + 2 * inset
    )
    path.transform(Matrix().apply {
        this.setRectToRect(rect,
            dstRect, Matrix.ScaleToFit.FILL)

    }
    )

    path.computeBounds(rect, true)

    println("--In normalisedPath fun--. rect 3: w = ${rect.width()},h = ${rect.height()}")
    println("--In normalisedPath fun--. rect 3: left = ${rect.left},top = ${rect.top}, right = ${rect.right}, bottom = ${rect.bottom}")
    println("--In normalisedPath fun--. rect 3: centerX = ${rect.centerX()}, centerY = ${rect.centerY()}")

    return path
}

fun combinedPath(paths: List<Path>): Path{
    val combinedPath = Path()
    paths.forEach {
        combinedPath.addPath(it)
    }
    return combinedPath
}


fun AndroidDrawPath.toBitmap(requiredSize: Int, _strokeWidth: Float): Bitmap{

    val bm = Bitmap.createBitmap(requiredSize, requiredSize, Bitmap.Config.ARGB_8888)

    val canvas = android.graphics.Canvas(bm)
    val paint = android.graphics.Paint().apply {
        color = android.graphics.Color.BLACK
        style = android.graphics.Paint.Style.STROKE
        strokeWidth = _strokeWidth
    }

    val nPath = normalisedPath(path, requiredSize, _strokeWidth)

    canvas.drawPath(nPath, paint)

    return bm

}

fun List<AndroidDrawPath>.toBitmap(
    requiredSize: Int,
    maxStrokeWidth: Float,
    basisStrokeWidth: Float,
    paintColor: Int = android.graphics.Color.BLACK,
): Bitmap{

    val boundingBox = RectF()

    val combinedPath = combinedPath(this.map { it.path })

    combinedPath.computeBounds(boundingBox, true)

    val maxSize = maxOf(boundingBox.width(), boundingBox.height())
    val sizeFactor = requiredSize / maxSize
    val basic_inset = basisStrokeWidth/2
    val extra_inset = (maxStrokeWidth - basisStrokeWidth)/2
    val inset = basic_inset + extra_inset
    val requiredNSize = maxSize.toInt() * sizeFactor

    val nPath = normalisedPath(combinedPath, requiredNSize.toInt(), maxStrokeWidth)

    nPath.computeBounds(boundingBox, true)

    val bm = Bitmap.createBitmap(
        (boundingBox.width() + maxStrokeWidth * 2).toInt(),
        (boundingBox.height() + maxStrokeWidth * 2).toInt(),
        Bitmap.Config.ARGB_8888)


    val canvas = android.graphics.Canvas(bm)
    val paint = android.graphics.Paint().apply {
        color = paintColor
        style = android.graphics.Paint.Style.STROKE
        strokeWidth = basisStrokeWidth
        isAntiAlias = false
    }

    canvas.drawPath(nPath, paint)

    println("bitmap from list paths: boundingbox w = ${boundingBox.width()}, boundingbox h = ${boundingBox.height()}")
    println("bitmap from list paths: bm w = ${bm.width}, bm h = ${bm.height}")

   return bm
}

fun List<AndroidDrawPath>.toBitmapUiOnly(
    requiredSize: Int,
    basisStrokeWidth: Float,
    paintColor: Int = android.graphics.Color.BLACK,
): Bitmap{
    val boundingBox = RectF()

    val combinedPath = android.graphics.Path()
    this.forEach(){
        combinedPath.addPath(it.path)
    }
    combinedPath.computeBounds(boundingBox, true)

    combinedPath.transform(Matrix().apply {
        setTranslate(
            -boundingBox.left, -boundingBox.top
        )
    }
    )

    val bm = Bitmap.createBitmap(boundingBox.width().toInt(), boundingBox.height().toInt(), Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bm)
    val paint = android.graphics.Paint().apply {
        color = paintColor
        style = android.graphics.Paint.Style.STROKE
        strokeWidth = basisStrokeWidth
        isAntiAlias = false
    }
    canvas.drawPath(combinedPath, paint)

    val sx = requiredSize.toFloat() / bm.width.toFloat()
    val sy = requiredSize.toFloat() / bm.height.toFloat()
    // println("sx = $sx, sy = $sy")

    val minScale = minOf(sx, sy)

    val dstWidth = minScale * bm.width
    val dstHeight = minScale * bm.height

    //println("bitmap from list paths: boundingbox w = ${boundingBox.width()}, boundingbox h = ${boundingBox.height()}")

    val scaledBitmap = Bitmap.createScaledBitmap(bm, dstWidth.toInt(), dstHeight.toInt(), false)
    //println("scaledBitmap w = ${scaledBitmap.width}, scaledBitmap h = ${scaledBitmap.height}")

    return scaledBitmap
}



