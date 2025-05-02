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

    fun numberSuccessesForLatestGame(mode: GameMode): Int{
        val game = gamesForMode(mode).maxBy { it.id }
        return game.drawResults.count {
            ResultCalculator.calculateResult(it, 4){} >= Meh().fromAccuracyPercent
        }
    }

    fun listAccuracies(): Map<GameMode,List<Int>>  = gamesHistory.groupBy { it.gameMode }.mapValues { (_, games) ->
        games.flatMap { it.drawResults }.map {
            ResultCalculator.calculateResult(it, 4){
                println("bitmap created: $it")
            }
        }
    }

    fun isNewTopScore(mode: GameMode): Boolean{

        println("GAMES MANAGER IS NEW TOP SCORE CALLED. GAMES HISTORY: ${gamesHistory.map { 
            Date(it.id.toLong()).let { 
                "${it.hours}:${it.minutes}:${it.seconds}"
            }
        }}")

        val scores = listAccuracies()[mode] ?: return true

        val sortedScores = scores.sortedDescending()

        println("GAMES MANAGER SORTED SCORES: $sortedScores")

        val latestScore = averageAccuracyForLatestGame(mode)
        val countLatestScore = sortedScores.count { it == latestScore }
        val latestScoreIndex = sortedScores.indexOf(latestScore)

        println("GAMES MANAGER LATEST SCORE: $latestScore")
        println("GAMES MANAGER COUNT LATEST SCORE: $countLatestScore")
        println("GAMES MANAGER LATEST SCORE INDEX: $latestScoreIndex")

        return latestScoreIndex == 0 && countLatestScore == 1

    }


}