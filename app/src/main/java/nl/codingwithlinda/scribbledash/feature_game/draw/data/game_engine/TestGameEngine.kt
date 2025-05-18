package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import android.graphics.Path
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class TestGameEngine(
    private val exampleProvider: DrawExampleProvider,
    private val gamesManager: GamesManager,
    private val accountManager: AccountManager
): GameEngineTemplate(
    exampleProvider = exampleProvider,
    gamesManager = gamesManager,
    accountManager = accountManager
) {
    override val gameMode: GameMode
        get() = GameMode.ENDLESS_MODE

    override fun getExample(): Path {
        exampleFlow.trySend(Path())
        return exampleProvider.getExample(R.drawable.glasses).examplePath.let {
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
        gamesManager.updateLatestGame(gameMode, results)
    }
    override suspend fun shouldStartNewGame(): Boolean {
        val limit = RatingFactory.getSuccessLimit(GameMode.ENDLESS_MODE)
        return this.getAccuracy() < limit
    }

    override fun isGameSuccessful(): Boolean {
        val limit = RatingFactory.getSuccessLimit(GameMode.ENDLESS_MODE)
        return this.getAccuracy() > limit
    }

    override suspend fun onUserInputDone() {

    }
}