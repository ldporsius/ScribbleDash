package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.util.error.GameError
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine

class EndlessGameEngine(
    private val exampleProvider: AndroidDrawExampleProvider,
): GameEngine {

    private var level: GameLevel = GameLevel.BEGINNER
    private val countDownTimer = CountDownTimer()
    private val pathCreator = StraightPathCreator()


    private var result: DrawResult = DrawResult(
        id = System.currentTimeMillis().toString(),
        level = level,
        examplePath = emptyList(),
        userPath = emptyList()
    )

    override fun setLevel(level: GameLevel) {
        this.level = level
    }

    override fun provideExample(): DrawResult {
        val example = exampleProvider.examples.random().examplePath

        result = result.copy(
            examplePath = example
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
        val paths = userInput.map{
            pathCreator.drawPath(it.path)
        }.map {
            it.path
        }
        result = result.copy(
            userPath = result.userPath + paths
        )
    }

    override fun endUserInput(callback: (ScResult<Any, GameError>) -> Unit ) {
        val accuracy = ResultCalculator.calculateResult(result, 4)
        val limit = RatingFactory.getSuccessLimit(GameMode.ENDLESS_MODE)
        val isSuccessful = accuracy >= limit
        when(isSuccessful){
            true -> {
                callback.invoke(ScResult.Success(result))
            }
            false -> {
                callback.invoke(ScResult.Failure(GameError.BelowStandardAccuracy))
            }
        }

    }

    override fun getResult(): DrawResult {
        return result
    }

}