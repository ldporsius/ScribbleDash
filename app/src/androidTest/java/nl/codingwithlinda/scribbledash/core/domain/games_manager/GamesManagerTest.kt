package nl.codingwithlinda.scribbledash.core.domain.games_manager

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.test.fakeDrawResultDifferentPaths
import nl.codingwithlinda.scribbledash.core.test.fakeDrawResultSamePaths
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GamesManagerTest{

    private val testData = fakeDrawResultSamePaths()

    @Before
    fun setup(){

    }

    @After
    fun tearDown(){
        GamesManager.INSTANCE.clear()
    }
    @Test
    fun testIsTopScore(){
        GamesManager.INSTANCE.updateLatestGame(GameMode.SPEED_DRAW, listOf(testData))

        val isTopScore = GamesManager.INSTANCE.isNewTopScore(GameMode.SPEED_DRAW)

        assertTrue(isTopScore)
    }

    @Test
    fun testIsNotTopScoreIfSameScoreTwice(){
        GamesManager.INSTANCE.updateLatestGame(GameMode.SPEED_DRAW, listOf(testData))
        GamesManager.INSTANCE.updateLatestGame(GameMode.SPEED_DRAW, listOf(testData))

        val isTopScore = GamesManager.INSTANCE.isNewTopScore(GameMode.SPEED_DRAW)

        assertFalse(isTopScore)
    }

    @Test
    fun testIsTopScoreIfTwoDifferent() = runBlocking{
        GamesManager.INSTANCE.addGame(GameMode.SPEED_DRAW, listOf(
            fakeDrawResultDifferentPaths()))
        delay(100)
        GamesManager.INSTANCE.addGame(GameMode.SPEED_DRAW, listOf(fakeDrawResultSamePaths()))


        val isTopScore = GamesManager.INSTANCE.isNewTopScore(GameMode.SPEED_DRAW)

        assertTrue(isTopScore)
    }
}