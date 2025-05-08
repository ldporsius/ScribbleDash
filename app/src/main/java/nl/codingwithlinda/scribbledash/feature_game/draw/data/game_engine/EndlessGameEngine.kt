package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.util.error.GameError
import nl.codingwithlinda.scribbledash.core.domain.model.AndroidDrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.domain.util.Error
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping.coordinatesToPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine

class EndlessGameEngine(
    private val exampleProvider: AndroidDrawExampleProvider,
    private val offsetParser: OffsetParser<AndroidDrawPath>,
    private val pathDrawer: PathDrawer<AndroidDrawPath>
): GameEngine {

    private var level: GameLevel = GameLevel.BEGINNER
    private val countDownTimer = CountDownTimer()

    private var result: AndroidDrawResult = AndroidDrawResult(
        id = System.currentTimeMillis().toString(),
        level = level,
        examplePath = emptyList(),
        userPath = emptyList()
    )

    override fun setLevel(level: GameLevel) {
        this.level = level
    }

    override fun provideExample(): AndroidDrawResult {
        val example = exampleProvider.examples.random()

        val path = coordinatesToPath(example.path)
        result = result.copy(
            examplePath = listOf(path)
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
           offsetParser.parseOffset(pathDrawer, it)
       }
        result = result.copy(
            userPath = paths
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

    override fun getResult(): AndroidDrawResult {
        return result
    }

}