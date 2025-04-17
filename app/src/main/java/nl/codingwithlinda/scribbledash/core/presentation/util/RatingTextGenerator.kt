package nl.codingwithlinda.scribbledash.core.presentation.util

import android.content.Context
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.model.Rating
import nl.codingwithlinda.scribbledash.core.domain.ratings.Good
import nl.codingwithlinda.scribbledash.core.domain.ratings.Great
import nl.codingwithlinda.scribbledash.core.domain.ratings.Meh
import nl.codingwithlinda.scribbledash.core.domain.ratings.Oops
import nl.codingwithlinda.scribbledash.core.domain.ratings.Woohoo

class RatingTextGenerator(
    private val context: Context
) {

    private val oopsArray: Array<String> = context.resources.getStringArray(R.array.oops_array)
    private val goodArray: Array<String> = context.resources.getStringArray(R.array.good_array)
    private val woohooArray: Array<String> = context.resources.getStringArray(R.array.woohoo_array)

    fun getRatingText(rating: Rating): UiText {
        return when (rating) {
            is Oops -> {
                val randomText = oopsArray.random()
                UiText.DynamicText(randomText)
            }
            is Meh -> {
                val randomText = oopsArray.random() //this is intentionally the same source as oops
                UiText.DynamicText(randomText)
            }
            is Good -> {
                val randomText = goodArray.random()
                UiText.DynamicText(randomText)
            }
            is Great -> {
                val randomText = goodArray.random() //this is intentionally the same source as good
                UiText.DynamicText(randomText)
            }
            is Woohoo -> {
                val randomText = woohooArray.random()
                UiText.DynamicText(randomText)
            }


            else -> UiText.DynamicText("not set")
        }
    }
}