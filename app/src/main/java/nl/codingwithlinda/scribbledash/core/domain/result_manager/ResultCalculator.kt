package nl.codingwithlinda.scribbledash.core.domain.result_manager

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.combinedPath
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.toBitmap
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import kotlin.math.roundToInt


typealias Accuracy = Int
object ResultCalculator {

    fun calculateResult(drawResult: DrawResult,
                        strokeWidthUser: Int,
                        printDebug: (Bitmap) -> Unit): Accuracy{


        val extraStrokeWidth = drawResult.level.toStrokeWidthFactor() * strokeWidthUser

        val bmExample = drawResult.examplePath.toBitmap(
            requiredSize = 500,
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


        val pixelMatches = pixelMatch(bmExample, bmUser)

       // println("pixelMatch result: $pixelMatches")
        //("count = $count")
        val correct = pixelMatches.count { it == PixelMatch.MATCH }
//        println("ignore = ${pixelMatches.count { it == PixelMatch.IGNORE }}")
//        println("correct = ${pixelMatches.count { it == PixelMatch.MATCH }}")
//        println("user only = ${pixelMatches.count { it == PixelMatch.USER_ONLY }}")

        val visibleUserPixels = visibleUserPixelCount(bmUser)
//        println("visibleUserPixels = $visibleUserPixels")

        val accuracy = (correct.toFloat() / visibleUserPixels.toFloat()) * 100
        //println("-- in resultcalculator --. accuracy = $accuracy")

        val missingLengthPenalty = getMissingLengthPenalty(
           drawResult
        )

        //println("-- in resultcalculator -- . missingLengthPenalty = $missingLengthPenalty")

        //Final Score (%) = Coverage (%) - Missing Length Penalty (%)
        return try {
            (accuracy - missingLengthPenalty).roundToInt().coerceAtLeast(0)
        }catch (e: Exception){
            e.printStackTrace()
            -1
        }
    }

    private fun getTotalPathLenght(paths: List<Path>): Float{
        return paths.fold(0f) { acc, path ->
            getPathLength(path) + acc
        }
    }
    private fun getPathLength(path: Path): Float
    {
        val pathMeasure = android.graphics.PathMeasure(path, false)
        return pathMeasure.length

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

    private val pathLengthMinPercent = 0.7f

    private fun getMissingLengthPenalty(drawResult: DrawResult): Float{
        //Missing Length Penalty (%) = 100 - (Total User Path
        //Length / Total Example Path Length * 100)
        val lengthE = getTotalPathLenght(drawResult.examplePath.map { it.path })
        val lengthU = getTotalPathLenght(drawResult.userPath.map { it.path })
        val deservesPenalty = lengthU / lengthE < pathLengthMinPercent
        val penalty = 100 - (lengthU / lengthE) * 100


        //println(" -- in resultcalculator -- . lengthE = $lengthE, lengthU = $lengthU, deservesPenalty = $deservesPenalty, penalty = $penalty")

        return if(deservesPenalty) penalty
        else 0f
    }
}