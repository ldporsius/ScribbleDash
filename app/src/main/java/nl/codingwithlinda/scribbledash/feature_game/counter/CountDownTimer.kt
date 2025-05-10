package nl.codingwithlinda.scribbledash.feature_game.counter

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CountDownTimer {

    companion object {
        const val DURATION_SECS = 4
        const val STARTTIME = 3
        const val INTERVAL = 1000L
    }

    val countdown = flow<Int> {
        repeat(DURATION_SECS){ i ->
            emit(STARTTIME - i)
            delay(INTERVAL)
        }
    }
}