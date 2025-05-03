package nl.codingwithlinda.scribbledash.core.domain.games_manager

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.di.TestAppModule
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.test.fakeDrawResultDifferentPaths
import nl.codingwithlinda.scribbledash.core.test.fakeDrawResultSamePaths
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GamesManagerTest{

    private val appContext = ApplicationProvider.getApplicationContext<Application>()

    private val testData = fakeDrawResultSamePaths()
    private lateinit var appModule: TestAppModule

    private lateinit var gamesManager: GamesManager
    @Before
    fun setup(){
        appModule = TestAppModule()
        gamesManager = GamesManager(
            appModule.gamesAccess
        )
    }

    @After
    fun tearDown(){
        runBlocking {
            gamesManager.clear()
        }
    }
    @Test
    fun testIsTopScore() = runBlocking{
        gamesManager.updateLatestGame(GameMode.SPEED_DRAW, listOf(testData))

        val isTopScore = gamesManager.isNewTopScore(GameMode.SPEED_DRAW)

        assertTrue(isTopScore)
    }

    @Test
    fun testIsTopScoreIfOnlyOneGameInHistory() = runBlocking{
        gamesManager.updateLatestGame(GameMode.SPEED_DRAW, listOf(testData))
        gamesManager.updateLatestGame(GameMode.SPEED_DRAW, listOf(testData))

        val isTopScore = gamesManager.isNewTopScore(GameMode.SPEED_DRAW)

        assertTrue(isTopScore)
    }
    @Test
    fun testIsNotTopScoreIfSameScoreTwice() = runBlocking{
        gamesManager.addGame(GameMode.SPEED_DRAW, listOf(testData))
        delay(500)
        gamesManager.addGame(GameMode.SPEED_DRAW, listOf(testData))

        val isTopScore = gamesManager.isNewTopScore(GameMode.SPEED_DRAW)

        assertFalse(isTopScore)
    }

    @Test
    fun testIsTopScoreIfTwoDifferent() = runBlocking{
        gamesManager.addGame(GameMode.SPEED_DRAW, listOf(
            fakeDrawResultDifferentPaths()))
        delay(100)
        gamesManager.addGame(GameMode.SPEED_DRAW, listOf(fakeDrawResultSamePaths()))

        val isTopScore = gamesManager.isNewTopScore(GameMode.SPEED_DRAW)

        assertTrue(isTopScore)
    }

    @Test
    fun testNumberSuccessesIsLimitedToGame() = runBlocking{
        gamesManager.addGame(GameMode.SPEED_DRAW, listOf(
            fakeDrawResultSamePaths()))
        delay(100)
        gamesManager.addGame(GameMode.SPEED_DRAW, listOf(fakeDrawResultSamePaths()))
        gamesManager.updateLatestGame(GameMode.SPEED_DRAW, listOf(fakeDrawResultSamePaths()))

        val numSuccess = gamesManager.numberSuccessesForLatestGame(GameMode.SPEED_DRAW)

        assertTrue(numSuccess == 2)
    }
}