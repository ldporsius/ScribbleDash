package nl.codingwithlinda.scribbledash.core.test

import android.app.Application
import androidx.room.Room
import nl.codingwithlinda.room_persistence.database.ScribbleDatabase
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.local_cache.RoomGamesAccess
import nl.codingwithlinda.scribbledash.core.di.AppModule
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.AndroidGameEngineFactory
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.SpeedDrawGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.TestGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class AndroidTestAppModule(
    private val application: Application
): AppModule {

    override val drawExampleProvider: AndroidDrawExampleProvider
        get() = AndroidDrawExampleProvider.getInstance(application)

    override val ratingTextGenerator: RatingTextGenerator
         = RatingTextGenerator(application)

    private val db = Room.databaseBuilder(
        application,
        ScribbleDatabase::class.java,
        "scribble_db"
    ).build()

    override val gamesAccess: RoomGamesAccess
        get() = RoomGamesAccess(db.gameDao)

    override val gamesManager: GamesManager
        get() = GamesManager(gamesAccess)


    override fun gameEngine(gameMode: GameMode): GameEngineTemplate {
        return when(gameMode){
            GameMode.ONE_ROUND_WONDER -> {
                TestGameEngine(
                    exampleProvider = drawExampleProvider,
                    gamesManager = gamesManager
                )
            }
            GameMode.SPEED_DRAW -> {
                SpeedDrawGameEngine(
                    exampleProvider = drawExampleProvider,
                    gamesManager = gamesManager
                )
            }
            GameMode.ENDLESS_MODE -> {
                TestGameEngine(
                    exampleProvider = drawExampleProvider,
                    gamesManager = gamesManager
                )
            }
        }

    }
}