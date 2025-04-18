package nl.codingwithlinda.scribbledash.core.data.draw_examples.util

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

fun normalisedPath(path: android.graphics.Path, requiredSize: Int, inset: Float): android.graphics.Path{
    val rect = RectF()
    path.computeBounds(rect, true)

    //println("bitmap from path. rect: w = ${rect.width()},h = ${rect.height()}")
    val sx = requiredSize.toFloat() / (rect.width()  + 2 * inset)
    val sy = requiredSize.toFloat() / (rect.height() + 2 * inset)
    val scaleMin = minOf(sx, sy)
    //println("sx = $sx, sy = $sy, scaleMin = $scaleMin")

    val dx = rect.left - inset
    val dy = rect.top - inset

    path.transform(Matrix().apply {
        setTranslate(
            -dx, -dy
        )
        postScale(
            scaleMin, scaleMin
        )
    })
    return path
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
    paintColor: Int = android.graphics.Color.BLACK
): Bitmap{

    val boundingBox = RectF()

    val combinedPath = android.graphics.Path()
    this.forEach(){
        combinedPath.addPath(it.path)
    }
    combinedPath.computeBounds(boundingBox, true)

    val maxSize = maxOf(boundingBox.width(), boundingBox.height())
    val sizeFactor = requiredSize / maxSize
    val basic_inset = basisStrokeWidth/2
    val extra_inset = (maxStrokeWidth - basisStrokeWidth)/2
    val inset = basic_inset + extra_inset
    val requiredNSize = maxSize.toInt() * sizeFactor + inset * 2

    boundingBox.set(0f, 0f, requiredNSize, requiredNSize)

    val nPath = normalisedPath(combinedPath, requiredNSize.toInt(), inset)

    val bm = Bitmap.createBitmap(boundingBox.width().toInt(), boundingBox.height().toInt(), Bitmap.Config.ARGB_8888)


    val canvas = android.graphics.Canvas(bm)
    val paint = android.graphics.Paint().apply {
        color = paintColor
        style = android.graphics.Paint.Style.STROKE
        strokeWidth = basisStrokeWidth
        isAntiAlias = false
    }

    canvas.drawPath(nPath, paint)

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



