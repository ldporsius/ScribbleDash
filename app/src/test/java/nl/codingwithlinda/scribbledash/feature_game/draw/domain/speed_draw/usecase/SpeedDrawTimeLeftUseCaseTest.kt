package nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.usecase

import org.junit.Assert.*
import org.junit.Test

class SpeedDrawTimeLeftUseCaseTest{

    @Test
    fun `test time left calculation - 120 seconds is rendered ok`(){
        val twoMinutes = 120

        val timeLeft = SpeedDrawTimeLeftUseCase.invoke(twoMinutes)

        assertEquals("02:00", timeLeft)

    }
}