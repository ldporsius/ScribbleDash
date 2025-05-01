package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.domain.ratings.RatingFactory
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameExampleNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameLevelNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameRootNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawNavRoute
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.OneRoundWonderScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.OneRoundWonderTopBar
import nl.codingwithlinda.scribbledash.feature_game.level.presentation.GameLevelScreen
import nl.codingwithlinda.scribbledash.feature_game.result.presentation.GameResultScreen
import nl.codingwithlinda.scribbledash.feature_game.result.presentation.GameResultViewModel
import nl.codingwithlinda.scribbledash.feature_game.result.presentation.state.GameResultAction
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.DrawExampleScreen
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.ShowExampleViewModel


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

        }



    }
}