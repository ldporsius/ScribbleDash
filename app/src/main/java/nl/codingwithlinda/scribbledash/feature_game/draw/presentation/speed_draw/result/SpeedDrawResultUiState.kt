package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.result

import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.ui.theme.surfaceLow
import nl.codingwithlinda.scribbledash.ui.theme.topscoreColor

data class SpeedDrawResultUiState(
    val ratingUi: RatingUi? = null,
    val isTopScore: Boolean = true,
    val successCount: Int = 0,
    val isHighestNumberOfSuccesses: Boolean = false
){
    fun backgroundColor(): Color {
        return if (isTopScore) {
            topscoreColor
        } else {
            surfaceLow
        }
    }
}
