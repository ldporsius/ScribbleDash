package nl.codingwithlinda.scribbledash.core.di

import nl.codingwithlinda.scribbledash.core.data.local_cache.RoomGamesAccess
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager

interface AppModule {

    val drawExampleProvider: DrawExampleProvider

    val gamesAccess: RoomGamesAccess

    val gamesManager: GamesManager
}