package nl.codingwithlinda.scribbledash.core.presentation.util

import nl.codingwithlinda.scribbledash.core.domain.model.Rating
import nl.codingwithlinda.scribbledash.core.domain.ratings.Good
import nl.codingwithlinda.scribbledash.core.domain.ratings.Great
import nl.codingwithlinda.scribbledash.core.domain.ratings.Meh
import nl.codingwithlinda.scribbledash.core.domain.ratings.Oops
import nl.codingwithlinda.scribbledash.core.domain.ratings.UnknownRating
import nl.codingwithlinda.scribbledash.core.domain.ratings.Woohoo
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi


class RatingMapper(
    private val ratingTextGenerator: RatingTextGenerator
) {
    fun toUi(
        rating: Rating,
        accuracyPercent: Int
    ): RatingUi {
        return when (rating) {
            is UnknownRating -> rating.toUi(accuracyPercent)
            is Oops -> rating.toUi(accuracyPercent)
            is Meh -> rating.toUi(accuracyPercent)
            is Good -> rating.toUi(accuracyPercent)
            is Great -> rating.toUi(accuracyPercent)
            is Woohoo -> rating.toUi(accuracyPercent)
            else -> throw IllegalArgumentException("Invalid rating: $rating")
        }
    }

    private fun UnknownRating.toUi(
        accuracyPercent: Int
    ): RatingUi {
        val text = ratingTextGenerator.getRatingText(this)
        return RatingUi(
            rating = this,
            title = UiText.DynamicText("Unknown"),
            text = text,
            accuracyPercent = accuracyPercent
        )
    }
    private fun Oops.toUi(
        accuracyPercent: Int
    ): RatingUi {
        val text = ratingTextGenerator.getRatingText(this)
        return RatingUi(
            rating = this,
            title = UiText.DynamicText("Oops"),
            text = text,
            accuracyPercent = accuracyPercent
        )
    }
    private fun Meh.toUi(
        accuracyPercent: Int
    ): RatingUi {
        val text = ratingTextGenerator.getRatingText(this)
        return RatingUi(
            rating = this,
            title = UiText.DynamicText("Meh"),
            text = text,
            accuracyPercent = accuracyPercent
        )
    }

    private fun Good.toUi(
        accuracyPercent: Int
    ): RatingUi {
        val text = ratingTextGenerator.getRatingText(this)
        return RatingUi(
            rating = this,
            title = UiText.DynamicText("Good"),
            text = text,
            accuracyPercent = accuracyPercent
        )
    }

    private fun Great.toUi(
        accuracyPercent: Int
    ): RatingUi {
        val text = ratingTextGenerator.getRatingText(this)
        return RatingUi(
            rating = this,
            title = UiText.DynamicText("Great"),
            text = text,
            accuracyPercent = accuracyPercent
        )
    }

    private fun Woohoo.toUi(
        accuracyPercent: Int
    ): RatingUi {
        val text = ratingTextGenerator.getRatingText(this)
        return RatingUi(
            rating = this,
            title = UiText.DynamicText("Woohoo"),
            text = text,
            accuracyPercent = accuracyPercent
        )
    }
}