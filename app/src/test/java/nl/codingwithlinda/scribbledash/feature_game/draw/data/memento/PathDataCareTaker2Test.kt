package nl.codingwithlinda.scribbledash.feature_game.draw.data.memento

import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import org.junit.Assert.*
import org.junit.Test

class PathDataCareTaker2Test{
    private val careTaker = PathDataCareTaker2()

    @Test
    fun `test canRedo`(){
        careTaker.save(PathData(id = "1", color = 0, path = emptyList()))

        careTaker.clear()

        assertFalse(careTaker.canRedo())

    }
}