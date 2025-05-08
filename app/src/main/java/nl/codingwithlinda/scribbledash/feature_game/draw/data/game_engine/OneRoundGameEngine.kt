package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import nl.codingwithlinda.scribbledash.core.data.util.error.GameError
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.model.CoordinatesDrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleCoordinatesDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine

class OneRoundGameEngine(
    private val exampleProvider: DrawExampleProvider,
    private val offsetParser: OffsetParser,

    ): GameEngine {
    private var level: GameLevel = GameLevel.BEGINNER

    private val countDownTimer = CountDownTimer()

    private var result = CoordinatesDrawResult(
        id = System.currentTimeMillis().toString(),
        level = level,
        examplePath = emptyList(),
        userPath = emptyList()
    )

    override fun setLevel(level: GameLevel) {
       this.level = level
    }
    override fun provideExample(): DrawResult {
        val example = exampleProvider.examples.random()

        result = result.copy(
            examplePath = listOf(example)
        )

        return result
    }

    override val countDown: Flow<Int>
        get() = countDownTimer.startCountdown()

    override val showExample: Flow<Boolean>
        get() = countDownTimer.startCountdown().transform {count ->
            emit(count > 0)
        }

    override fun processUserInput(userInput: List<PathData>) {
       val paths = userInput.map {
           offsetParser.parseOffset(it)
       }
        val userPath = SimpleCoordinatesDrawPath(
            paths = paths
        )
        result = result.copy(
            userPath = result.userPath + listOf(userPath)
        )
    }

    override fun endUserInput(callback: (ScResult<Any, GameError>) -> Unit ) {
        callback.invoke(ScResult.Success(result))
    }

    override fun getResult(): DrawResult {
        return result
    }

}