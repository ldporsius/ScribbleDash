package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessRootNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameExampleNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameLevelNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameRootNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawNavRoute
import nl.codingwithlinda.scribbledash.feature_game.level.presentation.GameLevelScreen


fun NavGraphBuilder.GameNavGraph(
    appModule: AndroidAppModule,
    navToHome: () -> Unit
) {

    composable<GameRootNavRoute> {

        val gameNavController = rememberNavController()
        NavHost(navController = gameNavController, startDestination = GameLevelNavRoute) {

            composable<GameLevelNavRoute> {
                GameLevelScreen(
                    actionOnClose = {
                        navToHome()
                    },
                    actionOnLevel = { level ->
                        ResultManager.INSTANCE.addResult(
                            level = level
                        )
                        ResultManager.INSTANCE.gameMode?.let { gameMode ->
                            when (gameMode) {
                                GameMode.ONE_ROUND_WONDER -> {
                                    gameNavController.navigate(GameExampleNavRoute)
                                }
                                GameMode.SPEED_DRAW -> {
                                    gameNavController.navigate(SpeedDrawNavRoute)
                                }
                                GameMode.ENDLESS_MODE -> {
                                    gameNavController.navigate(EndlessRootNavRoute)
                                }
                            }
                        }
                    }
                )
            }

           oneRoundWonderNavGraph(
               gameNavController = gameNavController,
               appModule = appModule,
               navToHome = navToHome
           )

            speedDrawNavGraph(
                gameNavController = gameNavController,
                appModule = appModule,
                navToHome = navToHome
            )
            endlessModeNavGraph(
                appModule = appModule
            )

        }


    }
}