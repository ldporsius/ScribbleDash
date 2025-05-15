package nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine

import android.graphics.Path
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class TestGameEngine(
    private val exampleProvider: DrawExampleProvider,
    private val gamesManager: GamesManager
): GameEngineTemplate(
    exampleProvider = exampleProvider,
    gamesManager = gamesManager
) {
    override val gameMode: GameMode
        get() = GameMode.ONE_ROUND_WONDER

    private var index = 3
    override fun getExample(): Path {

        return exampleProvider.getExample(R.drawable.eye).examplePath.let {
            combinedPath(it)
        }
    }
    override fun saveResult(result: DrawResult) {
        results.clear()
        results.add(result)
    }
    override suspend fun shouldStartNewGame(): Boolean {
       return true
    }

    override fun isGameSuccessful(): Boolean {
        return true
    }

    override suspend fun onUserInputDone() {
        //nothing
    }
}