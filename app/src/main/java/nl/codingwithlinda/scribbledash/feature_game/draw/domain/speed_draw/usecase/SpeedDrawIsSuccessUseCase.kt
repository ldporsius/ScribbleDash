package nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.usecase

import nl.codingwithlinda.scribbledash.core.domain.ratings.Oops
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory

class SpeedDrawIsSuccessUseCase {

    fun invoke(accuracy: Int): Boolean {
        val rating = RatingFactory.getRating(accuracy)

        val success = rating.toAccuracyPercent > Oops().toAccuracyPercent

        return success
    }
}