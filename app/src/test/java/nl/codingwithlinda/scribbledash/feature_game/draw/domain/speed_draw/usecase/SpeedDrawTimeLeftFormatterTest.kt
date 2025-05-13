package nl.codingwithlinda.scribbledash.feature_game.draw.domain.speed_draw.usecase

import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.SpeedDrawTimeLeftFormatter
import org.junit.Assert.*
import org.junit.Test

class SpeedDrawTimeLeftFormatterTest{

    @Test
    fun `test time left calculation - 120 seconds is rendered ok`(){
        val twoMinutes = 120

        val timeLeft = SpeedDrawTimeLeftFormatter.invoke(twoMinutes)

        assertEquals("02:00", timeLeft)

    }
}