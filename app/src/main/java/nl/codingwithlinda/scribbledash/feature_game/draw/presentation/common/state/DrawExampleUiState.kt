package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state

import android.graphics.Path
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer

@Immutable
data class DrawExampleUiState(
    val drawPath: Path = Path(),
    val pathColor: Color = Color.Black,
    val counter: Int = CountDownTimer.STARTTIME,
)
