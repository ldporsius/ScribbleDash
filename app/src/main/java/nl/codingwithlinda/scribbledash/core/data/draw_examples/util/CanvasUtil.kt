package nl.codingwithlinda.scribbledash.core.data.draw_examples.util

import android.graphics.Matrix
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.centerPath(path: android.graphics.Path){
    var tMatrix = Matrix()
    val bounds = path.asComposePath().getBounds()

    println("bounds: $bounds")
    println("bounds height: ${bounds.height}")
    println("canvas height: ${this.size.height}")
    val sx = this.size.width / bounds.width
    val sy = this.size.height / bounds.height
    val sxMin = minOf(sx, sy) *.75f

    println("sxMin: $sxMin")
    val dx = (bounds.left * sxMin)
    val dx2 = (this.size.width / 2) - (bounds.width * sxMin)/2
    val dy = (-bounds.top * sxMin)
    val dy2 = (this.size.height / 2) - (bounds.height * sxMin)/2
    println("dy: $dy")
    tMatrix =  tMatrix.apply {
        setScale(sxMin, sxMin)
        postTranslate(-dx, dy)
        postTranslate(dx2,dy2 )

    }
    path.transform(
        tMatrix
    )
}