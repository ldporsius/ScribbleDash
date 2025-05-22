package nl.codingwithlinda.scribbledash.core.domain.result_manager

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import android.graphics.Color as Color2
import nl.codingwithlinda.scribbledash.core.data.util.toBitmap
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import kotlin.math.roundToInt


typealias Accuracy = Int
object ResultCalculator {

    const val VISIBILITY_TRESHOLD = 128
    const val AVERAGE_STROKE_WIDTH = 4

    fun calculateResult(drawResult: DrawResult,

                        ): Accuracy{
        return calculateResult(drawResult, AVERAGE_STROKE_WIDTH){}
    }
    fun calculateResult(drawResult: DrawResult,
                        strokeWidthUser: Int,
                        printDebug: (Bitmap) -> Unit): Accuracy{


        val extraStrokeWidth = drawResult.level.toStrokeWidthFactor() * strokeWidthUser

        val examplePaths = drawResult.examplePath
        val userPath = drawResult.userPath

        println("RESULT CALCULATOR: examplePaths.size = ${examplePaths.size}, userPath.size = ${userPath.size}")

        val bmExample = examplePaths.toBitmap(
            requiredSize = 500,
            maxStrokeWidth = extraStrokeWidth.toFloat(),
            basisStrokeWidth = extraStrokeWidth.toFloat(),
        )
        printDebug(bmExample)

        val bmUser = userPath
            .toBitmap(
            requiredSize = 500,
            maxStrokeWidth = extraStrokeWidth.toFloat(),
            basisStrokeWidth =  strokeWidthUser.toFloat(),
            Color.Red.toArgb()
        )

        val userBitmap = Bitmap.createBitmap(bmExample.width, bmExample.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(userBitmap)
        canvas.drawBitmap(bmUser, 0f, 0f, null)
        printDebug(userBitmap)

        //print out the debug bitmap
        val debugBitmap = bmExample.copy(Bitmap.Config.ARGB_8888, true)
        val canvasDebug = Canvas(debugBitmap)
        canvasDebug.drawBitmap(bmUser, 0f, 0f, null)
        printDebug(debugBitmap)

        val pixelMatches = pixelMatch(bmExample, userBitmap)

        val correct = pixelMatches.count { it == PixelMatch.MATCH }
        println("RESULT CALCULATOR NUMBER PIXELS THAT MATCH = ${correct}")

        val visibleUserPixels = visiblePixelCount(userBitmap)
        //println("visibleUserPixels = $visibleUserPixels")

        val accuracy = (correct.toFloat() / visibleUserPixels.toFloat()) * 100
        //println("-- in resultcalculator --. accuracy = $accuracy")

        val missingLengthPenalty = getMissingLengthPenalty(
           examplePaths, userPath
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

    private fun isColorVisible(color: Int, treshHold: Int): Boolean{
        return Color2.alpha(color) > treshHold
    }
    private fun visiblePixelCount(bitmap: Bitmap, treshHold: Int = VISIBILITY_TRESHOLD): Int{
        val pixels = getPixelArray(bitmap)
        return pixels.count {
            Color2.alpha(it) > treshHold
        }
    }

    private fun pixelMatch(bmExample: Bitmap, bmUser: Bitmap): List<PixelMatch>{
        val pixelsExample = getPixelArray(bmExample)
        val pixelsUser = getPixelArray(bmUser)
        //println("pixelsExample.size = ${pixelsExample.size}, pixelsUser.size = ${pixelsUser.size}")
        if(pixelsExample.size != pixelsUser.size) throw Exception("pixel arrays are not the same size")


       val result= pixelsUser.mapIndexed { index, pixUser ->

           val pixExample = pixelsExample.getOrElse(index, {Color2.TRANSPARENT})
           val isVisibleUser = isColorVisible(pixUser, 128)
           val isVisibleExample = isColorVisible(pixExample, 128)

           val toIgnore = !isVisibleExample && !isVisibleUser
           val isMatch = isVisibleExample && isVisibleUser
           val isUserOnly = isVisibleExample && !isVisibleUser

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

    private fun getMissingLengthPenalty(example: List<Path>, user: List<Path>): Float{
        //Missing Length Penalty (%) = 100 - (Total User Path
        //Length / Total Example Path Length * 100)
        val lengthE = getTotalPathLenght(example)
        val lengthU = getTotalPathLenght(user)
        val deservesPenalty = lengthU / lengthE < pathLengthMinPercent
        val penalty = 100 - (lengthU / lengthE) * 100


        //println(" -- in resultcalculator -- . lengthE = $lengthE, lengthU = $lengthU, deservesPenalty = $deservesPenalty, penalty = $penalty")

        return if(deservesPenalty) penalty
        else 0f
    }
}