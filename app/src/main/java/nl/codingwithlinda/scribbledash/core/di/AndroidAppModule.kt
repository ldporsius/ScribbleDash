package nl.codingwithlinda.scribbledash.core.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import nl.codingwithlinda.room_persistence.database.ScribbleDatabase
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.local_cache.RoomGamesAccess
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.model.tools.MyShoppingCart
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.AndroidGameEngineFactory
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

val Context.DataStore by preferencesDataStore("coins")
val DATASTORE_BALANCE_KEY = intPreferencesKey("balance")

class AndroidAppModule(
    private val application: Application
): AppModule {

    override val drawExampleProvider: AndroidDrawExampleProvider
        get() = AndroidDrawExampleProvider.getInstance(application)

    override val ratingTextGenerator: RatingTextGenerator = RatingTextGenerator(application)

    private val db = Room.databaseBuilder(
        application,
        ScribbleDatabase::class.java,
        "scribble_db"
    ).build()
    
    override val gamesAccess: RoomGamesAccess
        get() = RoomGamesAccess(db.gameDao)

    override val gamesManager: GamesManager
        get() = GamesManager(gamesAccess)

    private val gameEngineFactory =  AndroidGameEngineFactory(
        exampleProvider = drawExampleProvider,
        gamesManager = gamesManager,
        accountManager = accountManager
    )
    override fun gameEngine(gameMode: GameMode): GameEngineTemplate {
        return gameEngineFactory.createEngine(gameMode)
    }

    override val accountManager: AccountManager
        get() = AccountManager.Instance(
            coroutineScope = CoroutineScope(Dispatchers.IO),
            dataStore = datastore)

    override val shoppingCart: MyShoppingCart = MyShoppingCart(datastore)

    override val datastore: DataStore<Preferences>
        get() = application.DataStore
}