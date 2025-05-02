package nl.codingwithlinda.scribbledash.feature_game.result.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.Rating
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.domain.util.BitmapPrinter
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator

class GameResultViewModel(
    private val resultCalculator: ResultCalculator,
    private val ratingTextGenerator: RatingTextGenerator,
    private val ratingFactory: RatingFactory,
    private val bitmapPrinter: BitmapPrinter
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

    fun getRatingUi(drawResult: DrawResult): RatingUi {
        val accuracy = calculateAccuracy(drawResult)
        val rating = ratingFactory.getRating(accuracy)
        println("GameResultViewModel.getRatingUi() accuracy: ${accuracy}")

        return ratingMapper.toUi(rating, accuracy)
    }
}