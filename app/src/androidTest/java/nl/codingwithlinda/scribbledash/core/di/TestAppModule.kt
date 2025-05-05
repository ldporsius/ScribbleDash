package nl.codingwithlinda.scribbledash.core.di

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import nl.codingwithlinda.room_persistence.database.ScribbleDatabase
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.data.local_cache.RoomGamesAccess
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager

class TestAppModule: AppModule {

    private val appContext = ApplicationProvider.getApplicationContext<Application>()
    override val drawExampleProvider: AndroidDrawExampleProvider
        get() = AndroidDrawExampleProvider.getInstance(appContext)

    val testDb = Room.inMemoryDatabaseBuilder<ScribbleDatabase>(appContext).build()
    override val gamesAccess: RoomGamesAccess
        get() = RoomGamesAccess(testDb.gameDao)
    override val gamesManager: GamesManager
        get() = GamesManager(gamesAccess)
}