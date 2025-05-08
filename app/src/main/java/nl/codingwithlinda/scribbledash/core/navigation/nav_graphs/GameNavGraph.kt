package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessRootNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameLevelNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameRootNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.OneRoundRootNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.util.GameModeNavigation
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.EndlessGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.data.game_engine.OneRoundGameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.level.presentation.GameLevelScreen


fun NavGraphBuilder.GameNavGraph(
    appModule: AndroidAppModule,
    navToHome: () -> Unit
) {

    composable<GameRootNavRoute> {

        val gameNavController = rememberNavController()
        NavHost(navController = gameNavController, startDestination = GameLevelNavRoute) {
            val exampleProvider = appModule.drawExampleProvider

            val offsetParser = AndroidOffsetParser
            val gameEngine = OneRoundGameEngine(
                exampleProvider = exampleProvider,
                offsetParser = offsetParser,
            )
            val endlessGameEngine = EndlessGameEngine(
                exampleProvider = exampleProvider,
                offsetParser = offsetParser,
            )
            composable<GameLevelNavRoute> {
                GameLevelScreen(
                    actionOnClose = {
                        navToHome()
                    },
                    actionOnLevel = { level ->
                        gameEngine.setLevel(level)
                        GameModeNavigation.gameMode.let { gameMode ->
                            when (gameMode) {
                                GameMode.ONE_ROUND_WONDER -> {
                                    gameNavController.navigate(OneRoundRootNavRoute)
                                }
                                GameMode.SPEED_DRAW -> {
                                    gameNavController.navigate(SpeedDrawNavRoute)
                                }
                                GameMode.ENDLESS_MODE -> {
                                    gameNavController.navigate(
                                        EndlessRootNavRoute
                                    )
                                }
                            }
                        }
                    }
                )
            }

           oneRoundWonderNavGraph(
               appModule = appModule,
               gameEngine = gameEngine,
               navToHome = navToHome
           )

            speedDrawNavGraph(
                gameNavController = gameNavController,
                appModule = appModule,
                gameEngine = gameEngine,
                navToHome = navToHome
            )
            endlessModeNavGraph(
                appModule = appModule,
                onNavHome = navToHome,
                gameEngine = endlessGameEngine
            )
        }
    }
}