package nl.codingwithlinda.scribbledash.feature_game.counter

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class CountDownSpeedDraw {

    companion object {
        const val DURATION_SECS = 120
        const val STARTTIME = 120
        const val INTERVAL = 1000L
    }
    private val isPaused = MutableStateFlow<Boolean>(true)

    private var currentCount = STARTTIME
    val startCountdown = flow {

            while (currentCount >= 0 ) {

                emit(currentCount)

                delay(INTERVAL)
                if (!isPaused.value) {
                    currentCount -= 1
                }
        }
    }

    fun pause(){
        isPaused.value = true
    }

    fun resume(){
        isPaused.value = false
    }
}