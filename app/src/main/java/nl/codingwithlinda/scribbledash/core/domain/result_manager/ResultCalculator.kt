package nl.codingwithlinda.scribbledash.core.domain.result_manager

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.toBitmap
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import kotlin.math.roundToInt


typealias Accuracy = Int
class ResultCalculator {

    fun calculateResult(drawResult: DrawResult,
                        strokeWidthUser: Int,
                        printDebug: (Bitmap) -> Unit): Accuracy{


        val extraStrokeWidth = drawResult.level.toStrokeWidthFactor() * strokeWidthUser

        val aspectRatio = aspectRatio(drawResult.examplePath.path, combinedPath(drawResult.userPath.map { it.path }))
        val bmExample = listOf( drawResult.examplePath).toBitmap(
            requiredSize = 500 * aspectRatio.roundToInt(),
            maxStrokeWidth = extraStrokeWidth.toFloat(),
            basisStrokeWidth = extraStrokeWidth.toFloat(),

        )
        val bmUser = drawResult.userPath.toBitmap(
            requiredSize = 500,
            maxStrokeWidth = extraStrokeWidth.toFloat(),
            basisStrokeWidth =  strokeWidthUser.toFloat(),
            Color.RED
        )

        //print out the debug bitmap
        val debugBitmap = bmExample.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(debugBitmap)
        canvas.drawBitmap(bmUser, 0f, 0f, null)
        printDebug(debugBitmap)

        if(!areSameSize(bmExample, bmUser)) return -1


        val pixelMatches = pixelMatch(bmExample, bmUser)

       // println("pixelMatch result: $pixelMatches")
        //("count = $count")
        val correct = pixelMatches.count { it == PixelMatch.MATCH }
//        println("ignore = ${pixelMatches.count { it == PixelMatch.IGNORE }}")
//        println("correct = ${pixelMatches.count { it == PixelMatch.MATCH }}")
//        println("user only = ${pixelMatches.count { it == PixelMatch.USER_ONLY }}")

        val visibleUserPixels = visibleUserPixelCount(bmUser)
//        println("visibleUserPixels = $visibleUserPixels")

        val accuracy = correct.toFloat() / visibleUserPixels.toFloat()
//        println("accuracy = $accuracy")

        return (accuracy * 100).roundToInt()
    }

    private fun combinedPath(paths: List<Path>): Path{
        val combinedPath = Path()
        paths.forEach {
            combinedPath.addPath(it)
        }
        return combinedPath
    }
    private fun aspectRatio(pExample: Path, pUser: Path): Float{
        val rectExample = android.graphics.RectF()
        pExample.computeBounds(rectExample, true)
        val rectUser = android.graphics.RectF()
        pUser.computeBounds(rectUser, true)
        val ratioExample = rectExample.width() / rectExample.height()
        val ratioUser = rectUser.width() / rectUser.height()
        return ratioUser / ratioExample
    }
    private fun areSameSize(bmExample: Bitmap, bmUser: Bitmap): Boolean{
        println("bmExample.width = ${bmExample.width}, bmExample.height = ${bmExample.height}")
        println("bmUser.width = ${bmUser.width}, bmUser.height = ${bmUser.height}")
        val sameSize = bmExample.width == bmUser.width && bmExample.height == bmUser.height

        return sameSize
    }
    private fun GameLevel.toStrokeWidthFactor(): Int{
        return when(this){
            GameLevel.BEGINNER -> 15
            GameLevel.CHALLENGING -> 7
            GameLevel.MASTER -> 4
        }
    }

    private fun visibleUserPixelCount(bmUser: Bitmap): Int{
        val pixels = getPixelArray(bmUser)
        return pixels.count { it != Color.TRANSPARENT }
    }

    private fun pixelMatch(bmExample: Bitmap, bmUser: Bitmap): List<PixelMatch>{
        val pixelsExample = getPixelArray(bmExample)
        val pixelsUser = getPixelArray(bmUser)

       val result= pixelsExample.zip(pixelsUser).map { (pixExample, pixUser) ->

           val isTransparentUser = pixUser == Color.TRANSPARENT
           val isTransparentExample = pixExample == Color.TRANSPARENT

           val toIgnore = isTransparentExample && isTransparentUser
           val isMatch = !isTransparentExample && !isTransparentUser
           //val isUserOnly = isTransparentExample && !isTransparentUser

           if(toIgnore) PixelMatch.IGNORE
           else if(isMatch) PixelMatch.MATCH
           else PixelMatch.USER_ONLY
       }

        return result
    }

    private fun getPixelArray(bm: Bitmap): Array<Int>{
        val pixels = IntArray(bm.width * bm.height)
        bm.getPixels(pixels, 0, bm.width, 0, 0, bm.width, bm.height)
        return pixels.toTypedArray()
    }

    private enum class PixelMatch{
        IGNORE,
        USER_ONLY,
        MATCH,
    }
}