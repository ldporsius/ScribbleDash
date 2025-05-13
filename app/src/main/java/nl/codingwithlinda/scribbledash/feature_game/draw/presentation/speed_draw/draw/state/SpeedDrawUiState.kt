package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.state

import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownSpeedDraw
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.SpeedDrawTimeController
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.usecase.SpeedDrawTimeLeftUseCase
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.SpeedDrawCounter
import nl.codingwithlinda.scribbledash.ui.theme.onSurface

data class SpeedDrawUiState(
    //val drawState: DrawState = DrawState.EXAMPLE,
    val timeLeftSeconds: Int = CountDownSpeedDraw.STARTTIME,
    val successes: Int = 0
){
    fun timeLeft(): String = SpeedDrawTimeLeftUseCase.invoke(timeLeftSeconds)
    fun timeLeftColor(): Color {
        val isTimeScarce = SpeedDrawTimeController.isTimeBelowLimit(
            timeLeft = timeLeftSeconds
        )

        return if (isTimeScarce) nl.codingwithlinda.scribbledash.ui.theme.error else onSurface
    }


}
