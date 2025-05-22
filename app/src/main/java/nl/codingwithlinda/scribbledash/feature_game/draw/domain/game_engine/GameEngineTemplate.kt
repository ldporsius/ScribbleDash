package nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.model.Rating
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.BasicPenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.MultiColorPenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.domain.ratings.RewardCalculator
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.navigation.util.GameModeNavigation.gameMode
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownSpeedDraw
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownTimer
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.MultiColorPathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_shop.presentation.model.toUi

abstract class GameEngineTemplate(
    private val gamesManager: GamesManager,
    private val exampleProvider: DrawExampleProvider,
    private val accountManager: AccountManager
) {
    private var level: GameLevel = GameLevel.BEGINNER

    private val countDownTimer = CountDownTimer()
    private val pathCreator = StraightPathCreator()
    private val resultCalculator = ResultCalculator
    private val ratingFactory = RatingFactory

    protected val results = mutableListOf<DrawResult>()

    //template method
    suspend fun start(){

        if (shouldStartNewGame()) {
            startGame()
        }
        val result = createDrawResult()
        saveResult(result)

        shouldShowExample.update { true }

        emitNewExample()

        countDownTimer.countdown.collect(){count ->
            countDown.update {
                count
            }
        }

        shouldShowExample.update { false }

    }

    //common functions
    fun setLevel(level: GameLevel) {
        this.level = level
    }
    val exampleFlow = Channel<Path>()
    val countDown = MutableStateFlow(3)

    val shouldShowExample
            = MutableStateFlow(true)

    private val _coloredPaths = MutableStateFlow<List<ColoredPath>>(emptyList())
    val coloredPaths = _coloredPaths.asStateFlow()

   fun updateColoredPaths(paths: List<PathData>, penProduct: PenProduct){
        val coloredPaths =  paths.map {
            splitPathIntoColors(penProduct, it.path)
        }.flatten()

        _coloredPaths.update {
            coloredPaths
        }
    }
    suspend fun processUserInput(userInput: List<PathData>) {

        val paths = userInput.map{
             pathCreator.drawPath(it.path)
        }.map {
            it.path
        }

        val result = getResult().copy(
            userPath = paths
        )
        saveResult(result)
        persistResult()
    }

    fun getResult() = results.lastOrNull() ?: createDrawResult()

    fun pauseGame(){
        drawingTimeCounter.pause()
    }
    fun resumeGame(){
        drawingTimeCounter.resume()
    }
    fun setStartTime(time: Int){
        drawingTimeCounter.setStartTime(time)
    }

    fun getAccuracy(): Int{
        if (results.isEmpty()) return 0
        return resultCalculator.calculateResult(getResult(),)
    }
    fun getRating() : Rating{
        val accuracy = resultCalculator.calculateResult(getResult(),)
        return ratingFactory.getRating(accuracy)
    }

    suspend fun numberSuccessesForLatestGame(): Int{
        val limit = ratingFactory.getSuccessLimit(gameMode)
        val accs = results.map {
            ResultCalculator.calculateResult(it,)
        }.filter { it >= limit }

        return accs.count()
    }

    suspend fun averageAccuracyForLatestGame(): Int{
        return gamesManager.averageAccuracyForLatestGame(gameMode)
    }
    suspend fun highestAccuracy(): Int{
        return gamesManager.highestAccuracyForGameMode(gameMode)
    }
    suspend fun isNewTopScore(): Boolean{
        return gamesManager.isNewTopScore(gameMode)
    }
    suspend fun isHighestNumberOfSuccesses(): Boolean{
        return gamesManager.isHighestNumberOfSuccesses(gameMode)
    }

    fun coinsEarnedInLastestGame(): Int{
        return RewardCalculator.calculateReward(getRating(), level)
    }
    /////////private functions////////////////////////////////
    private suspend fun startGame(){
        results.clear()
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
        val example = getExample()
        saveExample(example)
        exampleFlow.send(example)

    }
    private fun saveExample(example: Path): DrawResult {

        val result = getResult().copy(
            examplePath = listOf(example)
        )
        saveResult(result)

        return result
    }

    fun splitPathIntoColors(penProduct: PenProduct, data: List<Offset>): List<ColoredPath> {

        val colors = when(penProduct){
            is BasicPenProduct -> listOf( penProduct.color)
            is MultiColorPenProduct -> penProduct.colors

            else -> emptyList()

        }
        val multiColorPathCreator = MultiColorPathCreator(colors)
        return multiColorPathCreator.drawPath(data).paths
    }

    //mandatory
    abstract val gameMode: GameMode
    abstract fun saveResult(result: DrawResult)
    abstract suspend fun shouldStartNewGame(): Boolean
    abstract suspend fun onUserInputDone()
    abstract fun isGameSuccessful(): Boolean

    //hooks
    open val drawingTimeCounter = CountDownSpeedDraw()
    open fun getExample(): Path{
        return exampleProvider.examples.random().examplePath.let {
            combinedPath(it)
        }
    }
    open suspend fun persistResult(){
        gamesManager.updateLatestGame(gameMode, results)

        val reward = RewardCalculator.calculateReward(getRating(), level)

        accountManager.processReward(reward)
    }
}