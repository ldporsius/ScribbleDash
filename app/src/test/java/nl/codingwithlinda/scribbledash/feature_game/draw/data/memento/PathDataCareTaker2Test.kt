package nl.codingwithlinda.scribbledash.feature_game.draw.data.memento

import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import org.junit.Assert.*
import org.junit.Test

class PathDataCareTaker2Test{
    private val careTaker = PathDataCareTaker()

    @Test
    fun `test canRedo is false after clear`(){
        careTaker.save(PathData(id = "1", color = 0, path = emptyList()))

        careTaker.clear()

        assertFalse(careTaker.canRedo())

    }
    @Test
    fun `test canRedo is false when nothing is undone`(){
        careTaker.save(PathData(id = "1", color = 0, path = emptyList()))

        assertFalse(careTaker.canRedo())

    }

    @Test
    fun `test canRedo is true when something is undone`(){
        careTaker.save(PathData(id = "1", color = 0, path = emptyList()))

        careTaker.undo()

        assertTrue(careTaker.canRedo())

    }

    @Test
    fun `test save limit 5`(){
        repeat(15) {
            careTaker.save(PathData(id = it.toString(), color = 0, path = emptyList()))
        }

        val res = careTaker.redo()
        assertEquals(5, res.size)

    }

    @Test
    fun `test undo`(){
        repeat(6) {
            careTaker.save(PathData(id = it.toString(), color = 0, path = emptyList()))
        }

        val resUndo = careTaker.undo()
        assertEquals(4, resUndo.size)
        val resRedo = careTaker.redo()

        println("RES UNDO: $resUndo")
        println("RES REDO: $resRedo")
        assertEquals(5, resRedo.size)

    }
}