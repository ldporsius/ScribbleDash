package nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw

object SpeedDrawTimeController{

    const val timeLimitSeconds = 30

    fun isTimeBelowLimit(timeLeft: Int): Boolean{
        return timeLeft <= timeLimitSeconds
    }
}