package nl.codingwithlinda.scribbledash.core.domain.ratings

import androidx.compose.ui.util.fastRoundToInt
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.model.Rating
import kotlin.math.ceil

object RewardCalculator {

    //basic rewards
    //1 2 4 6

    //factors per game level
    //beginner = 0.5
    //challenging = 1
    //master = 1.75

    fun calculateReward(rating: Rating, gameLevel: GameLevel): Int {
        if (rating is UnknownRating) return 0
        if (rating is Oops) return 1

        return ceil(basicReward(rating) * gameLevel.getFactor()).fastRoundToInt()

    }
    private fun basicReward(rating: Rating): Int {
        if (rating is UnknownRating) return 0
        if (rating is Oops) return 1
        if (rating is Meh) return 2
        if (rating is Good) return 4
        if (rating is Great) return 6

        return 0
    }
    private fun GameLevel.getFactor(): Double {
        return when (this) {
            GameLevel.BEGINNER -> 0.5
            GameLevel.CHALLENGING -> 1.0
            GameLevel.MASTER -> 1.75
        }
    }

}