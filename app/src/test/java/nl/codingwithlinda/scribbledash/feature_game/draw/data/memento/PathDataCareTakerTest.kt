package nl.codingwithlinda.scribbledash.feature_game.draw.data.memento

import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import org.junit.Assert.*
import org.junit.Test

class PathDataCareTakerTest{

    val careTaker = PathDataCareTaker()

    @Test
    fun testSave(){
        val pathData = PathData(
            id = "6",
            color = 10,
            path = listOf()
        )
        val list = (0..5).map {
            pathData.copy(
                id = it.toString(),
                color = it
            )
        }

        list.forEach {
            careTaker.save(it)
        }

        val undoRes = careTaker.undo()
        println("UNDO RES 1: $undoRes")
        val res = careTaker.redo()

        println("REDO RES 1: $res")

        assertEquals(list, res)

        val undos = careTaker.undo()
        println("UNDO RES 2: $undoRes")

        val newList = undos.plusElement(pathData)
        careTaker.save(pathData)

        val redo = careTaker.redo()
        println("REDO RES 2: $redo")

        assertEquals(newList, redo)


        repeat(10){
            careTaker.undo()
        }
        val redoRes = careTaker.redo()
        println("REDO RES 3: $redoRes")

    }
}