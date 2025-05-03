package nl.codingwithlinda.room_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val dateCreated: Long,
    val gameMode: String,
    val scores: String
)
