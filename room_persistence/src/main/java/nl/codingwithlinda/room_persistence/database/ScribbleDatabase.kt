package nl.codingwithlinda.room_persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nl.codingwithlinda.room_persistence.access.GameDao
import nl.codingwithlinda.room_persistence.model.GameEntity

@Database(
    entities = [
        GameEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ScribbleDatabase: RoomDatabase(){

    abstract val gameDao: GameDao
}