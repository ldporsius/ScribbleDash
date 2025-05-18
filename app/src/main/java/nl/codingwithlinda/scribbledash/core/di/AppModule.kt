package nl.codingwithlinda.scribbledash.core.di

import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.data.local_cache.RoomGamesAccess
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

interface AppModule {

    val drawExampleProvider: DrawExampleProvider

    val gamesAccess: RoomGamesAccess

    val gamesManager: GamesManager

    fun gameEngine(gameMode: GameMode): GameEngineTemplate

    val ratingTextGenerator: RatingTextGenerator

    val accountManager: AccountManager
}