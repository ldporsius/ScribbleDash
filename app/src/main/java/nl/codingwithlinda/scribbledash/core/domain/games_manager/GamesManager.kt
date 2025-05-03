package nl.codingwithlinda.scribbledash.core.domain.games_manager

import androidx.compose.ui.util.fastSumBy
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import nl.codingwithlinda.scribbledash.core.domain.local_cache.LocalCacheAccess
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.Game
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.Meh
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import java.util.Date
import kotlin.math.roundToInt

class GamesManager(
    private val gamesAccess: LocalCacheAccess<Game, GameMode>
) {

    private suspend fun gamesForMode(mode: GameMode): List<Game> {
        return gamesAccess.readAllFlow(mode).firstOrNull() ?: emptyList()
    }

    suspend fun addGame(mode: GameMode, drawResults: List<DrawResult>) {

        val scores = listAccuracies(drawResults)
        val game = Game(
            id = System.currentTimeMillis().toString(),
            dateCreated = System.currentTimeMillis(),
            gameMode = mode,
            scores = scores
        )
        gamesAccess.create(game)
    }

    suspend fun updateLatestGame(mode: GameMode, drawResults: List<DrawResult>) {
        val latestGame = gamesForMode(mode).maxByOrNull { it.id }

        if (latestGame == null) {
            addGame(mode, drawResults)
            return
        }
        val currentResults = latestGame.scores
        val newResults = listAccuracies(drawResults)
        val updatedGame = latestGame.copy(scores = currentResults + newResults)

        gamesAccess.update(updatedGame)

    }

    suspend fun clear(){
        gamesAccess.deleteAll()
    }


    suspend fun averageAccuracyForLatestGame(mode: GameMode): Int{
        val game = gamesForMode(mode).maxByOrNull { it.id } ?: return 0
        val totalAccuracy = game.scores.average()
        return totalAccuracy.roundToInt()
    }

    private fun countSuccesses(drawResults: List<DrawResult>): Int{
        return drawResults.count {
            ResultCalculator.calculateResult(it, 4){} >= Meh().fromAccuracyPercent
        }
    }

    suspend fun numberSuccessesForLatestGame(mode: GameMode): Int{
        val game = gamesForMode(mode).maxByOrNull { it.id } ?: return 0
        return game.scores.count { it >= Meh().fromAccuracyPercent }
    }

    suspend fun numberSuccessesForMode(mode: GameMode): Map<Game, Int>{
        val games = gamesForMode(mode)
        val result = games.associateWith { game ->
            game.scores.count { it >= Meh().fromAccuracyPercent }
        }
        return result
    }


    private fun listAccuracies(drawResults: List<DrawResult>): List<Int> {
        return drawResults.map {
            ResultCalculator.calculateResult(it, 4){
                //println("bitmap created: $it")
            }
        }
    }

    private suspend fun averageAccuracyPerGame(mode: GameMode): Map<Game, Int> {
        val games = gamesForMode(mode)
        val result = games.associateWith { game ->
            game.scores.average().roundToInt()
        }
        return result
    }

    private suspend fun isHighestScoreForGameMode(mode: GameMode): Boolean{
        if (gamesForMode(mode).size == 1) return true

        val avgScores = averageAccuracyPerGame(mode)

        println("GAMES MANAGER AVG SCORES: $avgScores")

        val highestScore = avgScores.values.maxOrNull() ?: 0
        val latestScore = averageAccuracyForLatestGame(mode)

        println("GAMES MANAGER HIGHEST SCORE: $highestScore")
        println("GAMES MANAGER LATEST SCORE: $latestScore")

        val isUniqueHighestScore = avgScores.count { it.value == latestScore } == 1
        println("GAMES MANAGER IS UNIQUE HIGHEST SCORE: $isUniqueHighestScore")

        return latestScore == highestScore && isUniqueHighestScore

    }

    suspend fun isHighestNumberOfSuccesses(mode: GameMode): Boolean{
        if (gamesForMode(mode).size == 1) return true

        val avgSuccesses = numberSuccessesForMode(mode)
//        val highestSuccesses = avgSuccesses.values.max()
        val latestSuccesses = numberSuccessesForLatestGame(mode)

        val isUniqueHighestSuccesses = avgSuccesses.count { it.value == latestSuccesses } == 1

        return isUniqueHighestSuccesses
    }

    suspend fun isNewTopScore(mode: GameMode): Boolean{

        val historyTimes = gamesAccess.readAllFlow(mode).firstOrNull()?.map {game->

                Date(game.id.toLong()).let {
                    "${it.hours}:${it.minutes}:${it.seconds}"
                }
            }

        println("GAMES MANAGER IS NEW TOP SCORE CALLED. GAMES HISTORY: $historyTimes")
        val isHighestScoreForGameMode = isHighestScoreForGameMode(mode)
        val isHighestNumberOfSuccesses = isHighestNumberOfSuccesses(mode)
        println("GAMES MANAGER HIGHEST SCORE FOR GAME MODE: ${isHighestScoreForGameMode}")
        println("GAMES MANAGER HIGHEST NUMBER OF SUCCESSES: ${isHighestNumberOfSuccesses}")

        return isHighestScoreForGameMode || isHighestNumberOfSuccesses


    }


}