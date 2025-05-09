package nl.codingwithlinda.scribbledash.feature_game.test_data

import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExample
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.test.fakePathCircle

class FakeExampleProvider: DrawExampleProvider {
    override val examples: List<DrawExample>
        get() = List(10){
            DrawExample(examplePath = listOf( fakePathCircle()))
        }
}
