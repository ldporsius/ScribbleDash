package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import nl.codingwithlinda.scribbledash.core.data.util.error.GameError
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.model.CoordinatesDrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.model.createTypeSafeDrawPath
import nl.codingwithlinda.scribbledash.core.domain.model.createTypeSafeDrawResult
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.core.domain.util.Error
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.CoordinatesDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine

class OneRoundGameEngine(
    private val exampleProvider: DrawExampleProvider,
    private val offsetParser: OffsetParser<AndroidDrawPath>,
    private val pathDrawer: PathDrawer<AndroidDrawPath>
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

        val drawPath = createTypeSafeDrawPath<CoordinatesDrawPath>(example)
        return createTypeSafeDrawResult<CoordinatesDrawResult>(result).copy(
            examplePath = listOf( drawPath)
        )
    }

    override val countDown: Flow<Int>
        get() = countDownTimer.startCountdown()

    override val showExample: Flow<Boolean>
        get() = countDownTimer.startCountdown().transform {count ->
            emit(count > 0)
        }

    override fun processUserInput(userInput: List<PathData>) {
       val paths = userInput.map {
           offsetParser.parseOffset(pathDrawer, it)
       }
        result = result.copy(
            userPath = paths
        )
    }

    override fun endUserInput(callback: (ScResult<Any, GameError>) -> Unit ) {
        callback.invoke(ScResult.Success(result))
    }

    override fun getResult(): DrawResult {
        return result
    }

}