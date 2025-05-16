package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.Oops
import nl.codingwithlinda.scribbledash.feature_game.counter.CountDownSpeedDraw
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class SpeedDrawGameEngine(
    private val exampleProvider: DrawExampleProvider,
    private val gamesManager: GamesManager,
): GameEngineTemplate(
    exampleProvider = exampleProvider,
    gamesManager = gamesManager
) {

    private var exampleIndex = 0
    private var examples = exampleProvider.examples
    override val gameMode: GameMode
        get() = GameMode.SPEED_DRAW

    override fun getExample(): Path {
        if (exampleIndex == 0) examples = examples.shuffled() //shuffle only when all examples were shown
        exampleIndex = (exampleIndex + 1 ) % examples.size
        return examples[exampleIndex].examplePath.let {
            combinedPath(it)
        }
    }
    override fun saveResult(result: DrawResult) {
        results.removeAll {
            it.id == result.id
        }
        results.add(result)
    }

    override suspend fun persistResult() {
        val counter = this.drawingTimeCounter
        println("SPEED DRAW GAME ENGINE HAS COUNTER VALUE: ${counter.timer.value}")
        val shouldPersist = counter.timer.value <= 0
        if (shouldPersist) {
            gamesManager.updateLatestGame(gameMode, results)
            println("SPEED DRAW GAME ENGINE PERSISTED RESULTS, size = ${results.size}")
            drawingTimeCounter.reset()
        }
    }
    override suspend fun shouldStartNewGame(): Boolean {
        val counter = this.drawingTimeCounter
        return counter.timer.value <= 0
    }

    override fun isGameSuccessful(): Boolean {

        val rating = getRating()

        val success = rating.toAccuracyPercent > Oops().toAccuracyPercent

        return success
    }

    override suspend fun onUserInputDone() {
        start()
    }
}