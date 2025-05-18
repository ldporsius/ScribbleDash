package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import nl.codingwithlinda.scribbledash.core.di.AppModule
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.OneRoundHostNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.OneRoundRootNavRoute
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawRoot
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.OneRoundWonderTopBar
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.GameResultScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.GameResultViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.state.GameResultAction

fun NavGraphBuilder.oneRoundWonderNavGraph(
    appModule: AppModule,
    gameEngine: GameEngineTemplate,
    navToHome: () -> Unit
) {

    navigation<OneRoundRootNavRoute>(startDestination = OneRoundHostNavRoute) {
        composable<OneRoundHostNavRoute>{

            val gameNavController = rememberNavController()
            NavHost(navController = gameNavController, startDestination = GameDrawNavRoute) {

                composable<GameDrawNavRoute> {
                    GameDrawRoot(
                        gameEngine = gameEngine,
                        gameNavController = gameNavController,
                        topBar = {
                            OneRoundWonderTopBar(
                                actionOnClose = navToHome,
                                modifier = Modifier.fillMaxWidth()
                                )
                        }
                    )
                }

                composable<GameResultNavRoute> {

                    val ratingTextGenerator = appModule.ratingTextGenerator

                    val viewModel = viewModel<GameResultViewModel>(
                        factory = viewModelFactory {
                            initializer {
                                GameResultViewModel(
                                    ratingTextGenerator = ratingTextGenerator,
                                    gameEngine = gameEngine
                                )
                            }
                        }
                    )


                    val ratingUi = remember {
                        viewModel.getRatingUi()
                    }

                    GameResultScreen(
                        examplePath = viewModel.examplePath,
                        userPath = viewModel.userPath,
                        ratingUi = ratingUi,
                        reward = gameEngine.coinsEarnedInLastestGame(),
                        onAction = { action ->
                            when (action) {
                                GameResultAction.Close -> {
                                    navToHome()
                                }
                                GameResultAction.TryAgain -> {
                                    gameNavController.popBackStack()
                                    gameNavController.navigate(GameDrawNavRoute) {
                                        this.launchSingleTop = true
                                    }
                                }
                            }
                        }
                    )

                }
            }
        }
    }
}