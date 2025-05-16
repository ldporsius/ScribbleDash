package nl.codingwithlinda.scribbledash.feature_game.counter

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.BuildConfig

class CountDownSpeedDraw {

    companion object {
        const val DEBUGSTARTTIME = 20
        const val STARTTIME = 120
        const val INTERVAL = 1000L
    }
    private val isPaused = MutableStateFlow<Boolean>(true)

    private fun startTime() = if (BuildConfig.DEBUG) DEBUGSTARTTIME else STARTTIME
    private var currentCount = startTime()
    private val _timer = MutableStateFlow<Int>(currentCount)
    val timer = _timer.asStateFlow()

    suspend fun startCountdown() {

        reset()
        while (currentCount > 0 ) {

            delay(INTERVAL)
            if (!isPaused.value) {
                currentCount -= 1
            }
            _timer.update {
                currentCount
            }
        }
    }

    fun setStartTime(time: Int){
        currentCount = time
    }
    fun pause(){
        isPaused.update {
            true
        }
    }

    fun resume(){
        isPaused.update {
            false
        }
    }
    fun reset(){
        currentCount = startTime()

    }
}