package nl.codingwithlinda.scribbledash.feature_game.counter

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.codingwithlinda.scribbledash.core.BaseUnitTest
import org.junit.Assert.*
import org.junit.Test

class CountDownTimerTest: BaseUnitTest(){

    private val countdownTimer = CountDownTimer()
    @Test
    fun `test countdown timer`() = runTest{

        countdownTimer.countdown.test {
            repeat(CountDownTimer.DURATION_SECS){
                val em0 = awaitItem()
                println("em: $em0")
                assertEquals(3 - it, em0)
            }
        }

    }
}