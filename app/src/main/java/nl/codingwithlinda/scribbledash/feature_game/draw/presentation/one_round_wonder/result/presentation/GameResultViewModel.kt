package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation

import androidx.core.graphics.plus
import androidx.lifecycle.ViewModel
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.domain.util.BitmapPrinter
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine

class GameResultViewModel(
    private val resultCalculator: ResultCalculator,
    private val ratingTextGenerator: RatingTextGenerator,
    private val bitmapPrinter: BitmapPrinter,
    private val gameEngine: GameEngine
): ViewModel() {

    private val ratingMapper = RatingMapper(ratingTextGenerator)

    private fun calculateAccuracy(drawResult: DrawResult): Int{
       val accuracy = resultCalculator.calculateResult(
            drawResult,
            strokeWidthUser = 4
        ){
            bitmapPrinter.printBitmap(it, "calculate_result_${drawResult.id}.png")
        }
        return accuracy
    }

    val examplePath = gameEngine.getResult().examplePath.reduce { acc, path ->
        acc + path
    }
    val userPath = gameEngine.getResult().userPath

    fun getRatingUi(): RatingUi {
        val drawResult = gameEngine.getResult()
        val accuracy = calculateAccuracy(drawResult)
        println("GameResultViewModel.getRatingUi() accuracy: ${accuracy}")

        return ratingMapper.toUi(accuracy)
    }
}