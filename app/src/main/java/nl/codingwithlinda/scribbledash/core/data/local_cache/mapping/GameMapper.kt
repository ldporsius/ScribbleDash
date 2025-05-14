package nl.codingwithlinda.scribbledash.core.data.local_cache.mapping

import androidx.compose.ui.util.fastJoinToString
import nl.codingwithlinda.room_persistence.model.GameEntity
import nl.codingwithlinda.scribbledash.core.data.util.error.ParseError
import nl.codingwithlinda.scribbledash.core.domain.model.Game
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult

fun GameEntity.toDomain(): ScResult<Game, ParseError.GameModeParseError>{

    val gameMode = stringToGameMode(this.gameMode) ?: return ScResult.Failure(ParseError.GameModeParseError)

    val scoresJson = scores.split(",").mapNotNull { it.toIntOrNull() }

    val game = Game(
        id = id,
        dateCreated= this.dateCreated,
        gameMode = gameMode,
        scores = scoresJson
    )

    return ScResult.Success(game)
}

fun stringToGameMode(gameMode: String): GameMode?{
    return try {
        GameMode.valueOf(gameMode)
    }catch (e: Exception){
        e.printStackTrace()
       null
    }
}
fun gameModeToString(gameMode: GameMode): String{
    return gameMode.name
}
fun Game.toRoom(): GameEntity{

    val scoresJson = scores.fastJoinToString(",")
    return GameEntity(
        id = this.id,
        dateCreated = this.dateCreated,
        gameMode = gameModeToString(this.gameMode),
        scores = scoresJson
    )
}