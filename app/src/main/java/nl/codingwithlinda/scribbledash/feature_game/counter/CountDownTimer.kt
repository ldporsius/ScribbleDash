package nl.codingwithlinda.scribbledash.feature_game.counter

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CountDownTimer {

    companion object {
        const val STARTTIME = 3000
        const val INTERVAL = 1000L
    }

    fun startCountdown() = flow<Int> {
        repeat(4){i ->
            emit(STARTTIME - i * INTERVAL.toInt())
            delay(INTERVAL)
        }
    }
}