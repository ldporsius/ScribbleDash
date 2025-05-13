package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.state

import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownSpeedDraw
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.SpeedDrawTimeController
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.SpeedDrawTimeLeftFormatter
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onSurface

data class SpeedDrawUiState(
    //val drawState: DrawState = DrawState.EXAMPLE,
    val timeLeftSeconds: Int = CountDownSpeedDraw.STARTTIME,
    val successes: Int = 0
){
    fun timeLeft(): String = SpeedDrawTimeLeftFormatter.invoke(timeLeftSeconds)
    fun timeLeftColor(): Color {
        val isTimeScarce = SpeedDrawTimeController.isTimeBelowLimit(
            timeLeft = timeLeftSeconds
        )

        return if (isTimeScarce) nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.error else onSurface
    }


}
