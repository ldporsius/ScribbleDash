package nl.codingwithlinda.scribbledash.core.test

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import nl.codingwithlinda.room_persistence.database.ScribbleDatabase
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.local_cache.RoomGamesAccess
import nl.codingwithlinda.scribbledash.core.di.AppModule
import nl.codingwithlinda.scribbledash.core.di.DataStore
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.OneRoundGameEngine
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

    private val testGameEngine = TestGameEngine(
        exampleProvider = drawExampleProvider,
        gamesManager = gamesManager,
        accountManager
    )
    private val oneRoundGameEngine = OneRoundGameEngine(
        exampleProvider = drawExampleProvider,
        gamesManager = gamesManager,
        accountManager
    )
    private val speedDrawGameEngine = SpeedDrawGameEngine(
        exampleProvider = drawExampleProvider,
        gamesManager = gamesManager,
        accountManager
    )

    override fun gameEngine(gameMode: GameMode): GameEngineTemplate {
        return when(gameMode){
            GameMode.ONE_ROUND_WONDER -> {
               oneRoundGameEngine
            }
            GameMode.SPEED_DRAW -> {
              speedDrawGameEngine
            }
            GameMode.ENDLESS_MODE -> {
                testGameEngine
            }
        }
    }

    override val accountManager: AccountManager
        get() = AccountManager(datastore)

    override val datastore: DataStore<Preferences>
        get() = application.applicationContext.DataStore
}