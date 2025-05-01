package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.state

import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.SpeedDrawTimeController
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.ui.theme.onSurface

data class SpeedDrawUiState(
    val drawState: DrawState = DrawState.EXAMPLE,
    val timeLeftSeconds: Int = 180,
    val timeLeft: String = "",
    val successes: String = ""
){

    fun timeLeftColor(): Color {
        val isTimeLeft = SpeedDrawTimeController.isTimeBelowLimit(
            timeLeft = timeLeftSeconds
        )

        return if (isTimeLeft) onSurface else nl.codingwithlinda.scribbledash.ui.theme.error
    }


}
