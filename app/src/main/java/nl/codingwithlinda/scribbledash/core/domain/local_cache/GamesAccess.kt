package nl.codingwithlinda.scribbledash.core.domain.local_cache

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.scribbledash.core.data.util.error.DatabaseError
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult

interface LocalCacheAccess<T, FILTER> {
    suspend fun create(item: T): ScResult<T, DatabaseError>
    suspend fun read(id: String): ScResult<T, DatabaseError>
    suspend fun update(item: T): ScResult<T, DatabaseError>
    suspend fun delete(id: String): ScResult<Unit, DatabaseError>
    suspend fun deleteAll()

    suspend fun readAll(): ScResult<List<T>, DatabaseError>
    fun readAllFlow(f: FILTER): Flow<List<T>>
}