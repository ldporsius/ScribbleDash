package nl.codingwithlinda.scribbledash.feature_game.draw.data.memento

import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import org.junit.Assert.*
import org.junit.Test

class XMLPathDataCareTaker2Test{
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
    fun `test undo and redo once`(){
        repeat(6) {
            careTaker.save(PathData(id = it.toString(), color = 0, path = emptyList()))
        }

        val resUndo = careTaker.undo()
        assertEquals(5, resUndo.size)
        val resRedo = careTaker.redo()

        println("RES UNDO: $resUndo")
        println("RES REDO: $resRedo")
        assertEquals(6, resRedo.size)

    }
    @Test
    fun `test undo and save once`(){
        repeat(1) {
            careTaker.save(PathData(id = it.toString(), color = 0, path = emptyList()))
        }

        val resUndo = careTaker.undo()
        assertEquals(0, resUndo.size)

        careTaker.save(PathData(id = "1", color = 1, path = emptyList()))
        val resRedo = careTaker.redo()

        println("RES UNDO: $resUndo")
        println("RES REDO: $resRedo")
        assertEquals(1, resRedo.size)

    }
    @Test
    fun `test save twice, undo twice and redo once`(){
        repeat(2) {
            careTaker.save(PathData(id = it.toString(), color = it, path = emptyList()))
        }

        val resUndo1 = careTaker.undo()
        assertEquals(1, resUndo1.size)
        println("RES UNDO 1: $resUndo1")

        val resUndo2 = careTaker.undo()
        assertEquals(0, resUndo2.size)
        println("RES UNDO 2: $resUndo2")

        val resRedo = careTaker.redo()
        println("RES REDO: $resRedo")

        assertEquals(1, resRedo.size)
        assertEquals(0, resRedo.first().color)

    }

    @Test
    fun `test save one, undo, redo, save one more, undo `(){
        repeat(1) {
            careTaker.save(PathData(id = it.toString(), color = it, path = emptyList()))
        }

        val resUndo1 = careTaker.undo()
        println("RES UNDO 1: $resUndo1")
        assertEquals(0, resUndo1.size)

        val redoRes = careTaker.redo()
        println("RES REDO 1: $redoRes")
        assertEquals(1, redoRes.size)

        careTaker.save(PathData(id = "1", color = 1, path = emptyList()))

        val resUndo2 = careTaker.undo()
        assertEquals(1, resUndo2.size)
        println("RES UNDO 2: $resUndo2")

        assertEquals(0, resUndo2.first().color)

    }

    @Test
    fun `test save many, undo once `(){
        repeat(18) {
            careTaker.save(PathData(id = it.toString(), color = it, path = emptyList()))
        }

        val resUndo1 = careTaker.undo()
        println("RES UNDO 1: $resUndo1")
        assertEquals(17, resUndo1.size)

    }
}