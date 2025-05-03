package nl.codingwithlinda.scribbledash.core.data.local_cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.codingwithlinda.room_persistence.access.GameDao
import nl.codingwithlinda.scribbledash.core.data.local_cache.mapping.toDomain
import nl.codingwithlinda.scribbledash.core.data.local_cache.mapping.toRoom
import nl.codingwithlinda.scribbledash.core.data.util.error.DatabaseError
import nl.codingwithlinda.scribbledash.core.data.util.error.ParseError
import nl.codingwithlinda.scribbledash.core.domain.local_cache.LocalCacheAccess
import nl.codingwithlinda.scribbledash.core.domain.model.Game
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult

class RoomGamesAccess(
    private val gameDao: GameDao
): LocalCacheAccess<Game, GameMode> {
    override suspend fun create(item: Game): ScResult<Game, DatabaseError> {
        gameDao.insertGame(item.toRoom())
        return ScResult.Success(item)
    }

    override suspend fun read(id: String): ScResult<Game, DatabaseError> {
       val res = gameDao.getGameById(id)
           ?: return ScResult.Failure(DatabaseError.GenericError("Game not found"))
        val gameRes = res.toDomain()

        when(gameRes){
            is ScResult.Failure -> return ScResult.Failure(DatabaseError.GenericError("Game not found"))
            is ScResult.Success ->
            return  ScResult.Success(gameRes.data)
        }
    }

    override suspend fun update(item: Game): ScResult<Game, DatabaseError> {
        gameDao.upsertGame(item.toRoom())
        return ScResult.Success(item)
    }

    override suspend fun delete(id: String): ScResult<Unit, DatabaseError> {
        gameDao.deleteGameById(id)
        return ScResult.Success(Unit)
    }

    override suspend fun deleteAll() {
        gameDao.deleteAllGames()
    }

    override suspend fun readAll(): ScResult<List<Game>, DatabaseError> {
        val games = gameDao.getAllGames().map { it.toDomain() }
            .filterIsInstance<ScResult.Success<Game, ParseError.GameModeParseError>>()
            .map { it.data }
        return ScResult.Success(games)
    }

    override fun readAllFlow(gameMode: GameMode): Flow<List<Game>> {
        val games = gameDao.getGamesByGameMode(gameMode.name)
            .map {
                it.map { gameEntity ->
                    gameEntity.toDomain()
                }.filterIsInstance<ScResult.Success<Game, ParseError.GameModeParseError>>()
                    .map { it.data }
            }
        return games
    }
}