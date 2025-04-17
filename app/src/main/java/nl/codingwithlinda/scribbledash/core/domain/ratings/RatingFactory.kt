package nl.codingwithlinda.scribbledash.core.domain.ratings

import nl.codingwithlinda.scribbledash.core.domain.model.Rating

object RatingFactory {

    fun getRating(accuracyPercent: Int): Rating {
        return when (accuracyPercent) {
            in 0..39 -> Oops()
            in 40..69 -> Meh()
            in 70..79 -> Good()
            in 80..89 -> Great()
            in 90..100 -> Woohoo()
            else -> UnknownRating()
        }
    }
}