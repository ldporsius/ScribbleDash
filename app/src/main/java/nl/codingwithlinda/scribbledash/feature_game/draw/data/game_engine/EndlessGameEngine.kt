package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class EndlessGameEngine(
    private val exampleProvider: AndroidDrawExampleProvider,
    private val gamesManager: GamesManager
): GameEngineTemplate(
    exampleProvider = exampleProvider,
    gamesManager = gamesManager
) {

    private val ratingFactory = RatingFactory
    override val gameMode: GameMode
        get() = GameMode.ENDLESS_MODE

    override fun saveResult(result: DrawResult) {
        results.removeAll {
            it.id == result.id
        }
        results.add(result)
    }
    override suspend fun shouldStartNewGame(): Boolean {
        val limit = ratingFactory.getSuccessLimit(GameMode.ENDLESS_MODE)
        return this.getAccuracy() > limit
    }

    override fun isGameSuccessful(): Boolean {
        val limit = ratingFactory.getSuccessLimit(GameMode.ENDLESS_MODE)
        return this.getAccuracy() > limit
    }

    override suspend fun onUserInputDone() {

    }
}