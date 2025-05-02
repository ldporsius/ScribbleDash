package nl.codingwithlinda.scribbledash.core.domain.games_manager

import androidx.compose.ui.util.fastSumBy
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.Game
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.Meh
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import java.util.Date

enum class GamesManager {
    INSTANCE;

    private val gamesHistory = mutableListOf<Game>()

    private fun gamesForMode(mode: GameMode): List<Game> {
        return gamesHistory.filter { it.gameMode == mode }
    }

    fun addGame(mode: GameMode, drawResults: List<DrawResult>) {
        val game = Game(
            id = System.currentTimeMillis().toString(),
            gameMode = mode,
            drawResults = drawResults
        )
        gamesHistory.add(game)
    }

    fun updateLatestGame(mode: GameMode, drawResults: List<DrawResult>) {
        val latestGame = gamesHistory.sortedBy { it.id }.lastOrNull { it.gameMode == mode }

        if (latestGame == null) {
            addGame(mode, drawResults)
            return
        }
        val currentResults = latestGame.drawResults
        val updatedGame = latestGame.copy(drawResults = currentResults + drawResults)
        gamesHistory[gamesHistory.indexOf(latestGame)] = updatedGame
    }

    fun clear(){
        gamesHistory.clear()
    }

    fun averageAccuracyForMode(mode: GameMode): Double {
        val games = gamesForMode(mode)
        val totalAccuracy = games.sumOf { game ->
            game.drawResults.fastSumBy {
                ResultCalculator.calculateResult(it, 4){}
            }
        }
        return totalAccuracy.toDouble() / (games.size * 100)
    }

    fun averageAccuracyForLatestGame(mode: GameMode): Int{
        val game = gamesForMode(mode).maxBy { it.id }
        val totalAccuracy = game.drawResults.fastSumBy {
            ResultCalculator.calculateResult(it, 4){}
        }
        return totalAccuracy / (game.drawResults.size)
    }

    private fun countSuccesses(drawResults: List<DrawResult>): Int{
        return drawResults.count {
            ResultCalculator.calculateResult(it, 4){} >= Meh().fromAccuracyPercent
        }
    }

    fun numberSuccessesForLatestGame(mode: GameMode): Int{
        val game = gamesForMode(mode).maxBy { it.id }
        return countSuccesses(game.drawResults)
    }

    fun numberSuccessesForMode(mode: GameMode): Map<Game, Int>{
        val games = gamesForMode(mode)
        val result = games.associateWith { game ->
           countSuccesses(game.drawResults)
        }
        return result
    }


    private fun listAccuracies(): Map<GameMode,List<Int>>  = gamesHistory.groupBy { it.gameMode }.mapValues { (_, games) ->
        games.flatMap { it.drawResults }.map {
            ResultCalculator.calculateResult(it, 4){
                //println("bitmap created: $it")
            }
        }
    }

    private fun averageAccuracyPerGame(mode: GameMode): Map<Game, Int> {
        val games = gamesForMode(mode)
        val result = games.associateWith { game ->
            game.drawResults.fastSumBy {
                ResultCalculator.calculateResult(it, 4){}
            }
        }
        return result
    }

    private fun isHighestScoreForGameMode(mode: GameMode): Boolean{
        if (gamesForMode(mode).size == 1) return true

        val avgScores = averageAccuracyPerGame(mode)

        println("GAMES MANAGER AVG SCORES: $avgScores")

        val highestScore = avgScores.values.max()
        val latestScore = averageAccuracyForLatestGame(mode)

        println("GAMES MANAGER HIGHEST SCORE: $highestScore")
        println("GAMES MANAGER LATEST SCORE: $latestScore")

        val isUniqueHighestScore = avgScores.count { it.value == latestScore } == 1
        println("GAMES MANAGER IS UNIQUE HIGHEST SCORE: $isUniqueHighestScore")

        return latestScore == highestScore && isUniqueHighestScore

    }

    fun isHighestNumberOfSuccesses(mode: GameMode): Boolean{
        if (gamesForMode(mode).size == 1) return true

        val avgSuccesses = numberSuccessesForMode(mode)
        val highestSuccesses = avgSuccesses.values.max()
        val latestSuccesses = numberSuccessesForLatestGame(mode)

        val isUniqueHighestSuccesses = avgSuccesses.count { it.value == latestSuccesses } == 1

        return isUniqueHighestSuccesses
    }

    fun isNewTopScore(mode: GameMode): Boolean{

        println("GAMES MANAGER IS NEW TOP SCORE CALLED. GAMES HISTORY: ${gamesHistory.map { 
            Date(it.id.toLong()).let { 
                "${it.hours}:${it.minutes}:${it.seconds}"
            }
        }}")

        val isHighestScoreForGameMode = isHighestScoreForGameMode(mode)
        val isHighestNumberOfSuccesses = isHighestNumberOfSuccesses(mode)
        println("GAMES MANAGER HIGHEST SCORE FOR GAME MODE: ${isHighestScoreForGameMode}")
        println("GAMES MANAGER HIGHEST NUMBER OF SUCCESSES: ${isHighestNumberOfSuccesses}")

        return isHighestScoreForGameMode || isHighestNumberOfSuccesses


    }


}