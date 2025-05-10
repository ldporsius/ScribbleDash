package nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine

import android.graphics.Path
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.model.Rating
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownSpeedDraw
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

abstract class GameEngineTemplate(
    private val gamesManager: GamesManager,
    private val exampleProvider: DrawExampleProvider,
) {
    private var level: GameLevel = GameLevel.BEGINNER

    private val countDownTimer = CountDownTimer()
    private val pathCreator = StraightPathCreator()
    private val resultCalculator = ResultCalculator
    private val ratingFactory = RatingFactory

    private var result = DrawResult(
        id = System.currentTimeMillis().toString(),
        level = level,
        examplePath = emptyList(),
        userPath = emptyList()
    )


    //template method
    suspend fun start(){
        if (shouldStartNewGame()) {
            startGame()
        }
        result = createDrawResult()

        shouldShowExample.update { true }
        emitNewExample()

        countDown.collect()

        shouldShowExample.update { false }
    }

    //common functions
    fun setLevel(level: GameLevel) {
        this.level = level
    }
    val exampleFlow = Channel<Path>()
    val countDown: Flow<Int>
            = countDownTimer.countdown

    val shouldShowExample
            = MutableStateFlow(true)

    suspend fun processUserInput(userInput: List<PathData>) {
        val paths = userInput.map{
            pathCreator.drawPath(it.path)
        }.map {
            it.path
        }
        result = result.copy(
            userPath = result.userPath + paths
        )
        gamesManager.updateLatestGame(gameMode, listOf(result))
    }

    fun getResult() = result.copy()

    fun pauseGame(){
        drawingTimeCounter.pause()
    }
    fun resumeGame(){
        drawingTimeCounter.resume()
    }
    suspend fun saveGame(){
        gamesManager.updateLatestGame(gameMode, listOf(result))
    }
    fun getAccuracy(): Int{
        return resultCalculator.calculateResult(result, 4)
    }
    fun getRating() : Rating{
        val accuracy = resultCalculator.calculateResult(result, 4)
        return ratingFactory.getRating(accuracy)
    }

    suspend fun numberSuccessesForLatestGame(): Int{
        return gamesManager.numberSuccessesForLatestGame(gameMode)
    }

    /////////private functions////////////////////////////////
    private suspend fun startGame(){
        gamesManager.addGame(gameMode, emptyList())
    }
    private fun createDrawResult(): DrawResult{
        return DrawResult(
            id = System.currentTimeMillis().toString(),
            level = level,
            examplePath = emptyList(),
            userPath = emptyList()
        )
    }

    private suspend fun emitNewExample(){
        val example = exampleProvider.examples.random().examplePath.let {
            combinedPath(it)
        }
        exampleFlow.send(example)

        saveExample(example)
    }
    private fun saveExample(example: Path): DrawResult {

        result = result.copy(
            examplePath = listOf(example)
        )

        return result
    }


    //mandatory
    abstract val gameMode: GameMode
    abstract suspend fun shouldStartNewGame(): Boolean
    abstract suspend fun onUserInputDone()
    abstract fun isGameSuccessful(): Boolean
    //hooks
    open val drawingTimeCounter = CountDownSpeedDraw()


}