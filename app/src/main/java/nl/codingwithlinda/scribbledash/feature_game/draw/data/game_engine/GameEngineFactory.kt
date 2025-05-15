package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineFactory
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class AndroidGameEngineFactory(
    private val exampleProvider: AndroidDrawExampleProvider,
    private val gamesManager: GamesManager
): GameEngineFactory {

    override fun createEngine(gameMode: GameMode): GameEngineTemplate {
        return when (gameMode) {
            GameMode.ONE_ROUND_WONDER -> OneRoundGameEngine(
                exampleProvider = exampleProvider,
                gamesManager = gamesManager
            )
            GameMode.SPEED_DRAW -> {
                SpeedDrawGameEngine(
                    exampleProvider = exampleProvider,
                    gamesManager = gamesManager
                )
            }
            GameMode.ENDLESS_MODE -> {
                EndlessGameEngine(
                    exampleProvider = exampleProvider,
                    gamesManager = gamesManager
                )
            }
        }
    }
}