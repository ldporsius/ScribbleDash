package nl.codingwithlinda.scribbledash.core.domain.games_manager

import kotlinx.coroutines.flow.firstOrNull
import nl.codingwithlinda.scribbledash.core.domain.local_cache.LocalCacheAccess
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.Game
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
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
        val currentScores = latestGame.scores
        val newResults = listAccuracies(drawResults)
        val updatedGame = latestGame.copy(scores = newResults)

        gamesAccess.update(updatedGame)

    }

    suspend fun clear(){
        gamesAccess.deleteAll()
    }


    suspend fun averageAccuracyForLatestGame(mode: GameMode): Int{
        val game = gamesForMode(mode).maxByOrNull { it.id } ?: return 0
        val totalAccuracy = game.scores.average()
        return try {
            totalAccuracy.roundToInt()
        }catch (e: Exception){
            0
        }
    }

    suspend fun numberSuccessesForLatestGame(mode: GameMode): Int{
        val game = gamesForMode(mode).maxByOrNull { it.id } ?: return 0
        val successLimit = RatingFactory.getSuccessLimit(mode)
        return game.scores.count { it >= successLimit }
    }

    suspend fun numberSuccessesForMode(mode: GameMode): Map<Game, Int>{
        val games = gamesForMode(mode)
        val result = games.associateWith { game ->
            val successLimit = RatingFactory.getSuccessLimit(mode)
            game.scores.count { it >= successLimit }
        }
        return result
    }

    suspend fun highestNumberOfSuccessesForMode(mode: GameMode):  Int{
        return numberSuccessesForMode(mode).values.maxOrNull() ?: 0
    }


    private fun listAccuracies(drawResults: List<DrawResult>): List<Int> {
        return drawResults.map {
            ResultCalculator.calculateResult(it, 4)
        }
    }

    private suspend fun averageAccuracyPerGame(mode: GameMode): Map<Game, Int> {
        val games = gamesForMode(mode)
        val result = games.associateWith { game ->
            try {
                game.scores.average().roundToInt()
            }catch (e: Exception){
                0
            }
        }
        return result
    }

    suspend fun highestAccuracyForGameMode(mode: GameMode): Int {
        val highestScore = gamesForMode(mode).maxOfOrNull {
            it.scores.max()
        } ?: 0
        return highestScore
    }

    suspend fun highestAverageAccuracyForGameMode(mode: GameMode): Int {
        val avgScores = averageAccuracyPerGame(mode)
        return avgScores.values.maxOrNull() ?: 0
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