package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw

object SpeedDrawTimeLeftFormatter {

    operator fun invoke(timeLeft: Int): String {
        val minutes = timeLeft / 60
        val seconds = timeLeft % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}