package nl.codingwithlinda.scribbledash.core.domain.draw_examples

import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

interface DrawExamples {
    fun getRandomExample(): DrawPath
}