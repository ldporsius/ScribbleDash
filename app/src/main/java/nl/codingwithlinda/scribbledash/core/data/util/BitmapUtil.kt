package nl.codingwithlinda.scribbledash.core.data.util

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF

fun normalisedPath(
    path: Path,
    requiredSize: Int,
    inset: Float,
): Path{

    val rect = RectF()
    path.computeBounds(rect, true)

    val sx = requiredSize.toFloat() / (rect.width() )
    val sy = requiredSize.toFloat() / (rect.height())
    val scaleMin = minOf(sx, sy)

    path.transform(Matrix().apply {
        setTranslate(-rect.left, -rect.top)
    }
    )
    path.computeBounds(rect, true)

    val dstRect = RectF(
        inset,
        inset,
        requiredSize + inset,
        rect.height() * scaleMin + 2 * inset
    )
    path.transform(Matrix().apply {
        this.setRectToRect(rect,
            dstRect, Matrix.ScaleToFit.START)

    }
    )

    return path
}

fun combinedPath(paths: List<Path>): Path{
    val combinedPath = Path()
    paths.forEach {
        combinedPath.addPath(it)
    }
    return combinedPath
}

fun List<Path>.toBitmap(
    requiredSize: Int,
    maxStrokeWidth: Float,
    basisStrokeWidth: Float,
    paintColor: Int = android.graphics.Color.BLACK,
): Bitmap{

    val boundingBox = RectF()

    val combinedPath = combinedPath(this)

    combinedPath.computeBounds(boundingBox, true)

    val maxSize = maxOf(boundingBox.width(), boundingBox.height())
    val sizeFactor = requiredSize / maxSize
//    val basic_inset = basisStrokeWidth/2
//    val extra_inset = (maxStrokeWidth - basisStrokeWidth)/2
//
    val requiredNSize = maxSize.toInt() * sizeFactor

    val nPath = normalisedPath(combinedPath, requiredNSize.toInt(), maxStrokeWidth)

    nPath.computeBounds(boundingBox, true)

    try {

        val bm = Bitmap.createBitmap(
            (boundingBox.width() + maxStrokeWidth * 2).toInt(),
            (boundingBox.height() + maxStrokeWidth * 2).toInt(),
            Bitmap.Config.ARGB_8888
        )


        val canvas = android.graphics.Canvas(bm)
        val paint = android.graphics.Paint().apply {
            color = paintColor
            style = android.graphics.Paint.Style.STROKE
            strokeWidth = basisStrokeWidth
            isAntiAlias = false
        }

        canvas.drawPath(nPath, paint)

//        println("bitmap from list paths: boundingbox w = ${boundingBox.width()}, boundingbox h = ${boundingBox.height()}")
//        println("bitmap from list paths: bm w = ${bm.width}, bm h = ${bm.height}")

        return bm
    }catch (e: Exception){
        e.printStackTrace()

    }
    return Bitmap.createBitmap(requiredSize, requiredSize, Bitmap.Config.ARGB_8888)

}

fun List<Path>.toBitmapUiOnly(
    requiredSize: Int,
    basisStrokeWidth: Float,
    paintColor: Int = android.graphics.Color.BLACK,
): Bitmap{
    val boundingBox = RectF()

    val combinedPath = Path()
    this.forEach(){
        combinedPath.addPath(it)
    }
    combinedPath.computeBounds(boundingBox, true)

    combinedPath.transform(Matrix().apply {
        setTranslate(
            -boundingBox.left, -boundingBox.top
        )
    }
    )

    try {

        val bm = Bitmap.createBitmap(
            boundingBox.width().toInt(),
            boundingBox.height().toInt(),
            Bitmap.Config.ARGB_8888
        )
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
    }catch (e: Exception){
        //ignore
    }
    return Bitmap.createBitmap(requiredSize, requiredSize, Bitmap.Config.ARGB_8888)
}



