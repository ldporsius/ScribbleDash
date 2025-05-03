package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.state

import android.graphics.Path
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer

data class DrawExampleUiState(
    val drawPaths: List<Path> = emptyList(),
    val pathColor: Color = Color.Black,
    val counter: Int = CountDownTimer.STARTTIME,
)
