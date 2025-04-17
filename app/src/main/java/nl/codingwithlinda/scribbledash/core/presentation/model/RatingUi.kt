package nl.codingwithlinda.scribbledash.core.presentation.model

import nl.codingwithlinda.scribbledash.core.domain.model.Rating
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

data class RatingUi(
    val rating: Rating,
    val accuracyPercent: Int,
    val title: UiText,
    val text: UiText,
)
