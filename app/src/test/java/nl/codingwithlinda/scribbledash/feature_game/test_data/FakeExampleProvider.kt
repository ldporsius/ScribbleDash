package nl.codingwithlinda.scribbledash.feature_game.test_data

import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

class FakeExampleProvider: DrawExampleProvider {
    override val examples: List<DrawPath>
        get() = List(10){
            FakeDrawPath(path = it.toString())
        }
}

class FakeDrawPath(
    override val path: String
): DrawPath