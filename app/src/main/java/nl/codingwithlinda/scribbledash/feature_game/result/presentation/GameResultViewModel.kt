package nl.codingwithlinda.scribbledash.feature_game.result.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import nl.codingwithlinda.scribbledash.core.domain.model.Rating
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator

class GameResultViewModel(
    private val ratingTextGenerator: RatingTextGenerator,
    private val ratingFactory: RatingFactory
): ViewModel() {

    private val ratingMapper = RatingMapper(ratingTextGenerator)

    private val accuracy = MutableStateFlow<Int>(-1)

    private fun getRandomRating(): Rating {
        val accuracy = (0 .. 100).random()

        this.accuracy.value = accuracy

        val rating = ratingFactory.getRating(accuracy)

        return rating
    }

    fun getRatingUi(): RatingUi {
        val rating = getRandomRating()
        println("GameResultViewModel.getRatingUi() accuracy: ${accuracy.value}")

        return ratingMapper.toUi(rating, accuracy.value)
    }
}