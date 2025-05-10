package nl.codingwithlinda.scribbledash.feature_game.test_data

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.scribbledash.core.data.util.error.DatabaseError
import nl.codingwithlinda.scribbledash.core.domain.local_cache.LocalCacheAccess
import nl.codingwithlinda.scribbledash.core.domain.model.Game
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult

class FakeGamesAccess: LocalCacheAccess<Game, GameMode> {
    override suspend fun create(item: Game): ScResult<Game, DatabaseError> {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: String): ScResult<Game, DatabaseError> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): ScResult<Unit, DatabaseError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun readAll(): ScResult<List<Game>, DatabaseError> {
        TODO("Not yet implemented")
    }

    override fun readAllFlow(f: GameMode): Flow<List<Game>> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Game): ScResult<Game, DatabaseError> {
        TODO("Not yet implemented")
    }
}