package nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.usecase

class SpeedDrawTimeLeftUseCase {

    operator fun invoke(timeLeft: Int): String {
        val minutes = timeLeft / 60
        val seconds = timeLeft % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}