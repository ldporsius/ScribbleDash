package nl.codingwithlinda.room_persistence.access

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.room_persistence.model.GameEntity

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<GameEntity>

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGameById(gameId: String): GameEntity?

    @Query("DELETE FROM games WHERE id = :gameId")
    suspend fun deleteGameById(gameId: String)

    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    @Query("UPDATE games SET scores = :scores WHERE id = :gameId")
    suspend fun updateGameScores(gameId: String, scores: String)

    @Upsert
    suspend fun upsertGame(game: GameEntity)


    @Query("SELECT * FROM games WHERE gameMode = :gameMode")
    fun getGamesByGameMode(gameMode: String): Flow<List<GameEntity>>

}