package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.Oops
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class SpeedDrawGameEngine(
    private val exampleProvider: AndroidDrawExampleProvider,
    private val gamesManager: GamesManager,
): GameEngineTemplate(
    exampleProvider = exampleProvider,
    gamesManager = gamesManager
) {

    override val gameMode: GameMode
        get() = GameMode.SPEED_DRAW

    override suspend fun shouldStartNewGame(): Boolean {
        val counter = this.drawingTimeCounter
        return counter.timer.value == 0
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