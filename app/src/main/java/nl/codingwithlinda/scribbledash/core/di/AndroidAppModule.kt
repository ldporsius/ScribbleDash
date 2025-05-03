package nl.codingwithlinda.scribbledash.core.di

import android.app.Application
import androidx.room.Room
import nl.codingwithlinda.room_persistence.database.ScribbleDatabase
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.local_cache.RoomGamesAccess
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator

class AndroidAppModule(
    private val application: Application
): AppModule {

    override val drawExampleProvider: AndroidDrawExampleProvider
        get() = AndroidDrawExampleProvider.getInstance(application)

    val ratingTextGenerator: RatingTextGenerator = RatingTextGenerator(application)

    private val db = Room.databaseBuilder(
        application,
        ScribbleDatabase::class.java,
        "scribble_db"
    ).build()
    
    override val gamesAccess: RoomGamesAccess
        get() = RoomGamesAccess(db.gameDao)

    override val gamesManager: GamesManager
        get() = GamesManager(gamesAccess)
}