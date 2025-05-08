package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.plus
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameExampleNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.OneRoundHostNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.OneRoundRootNavRoute
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping.coordinatesToPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.OneRoundWonderScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.OneRoundWonderTopBar
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.DrawExampleScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.ShowExampleViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.GameResultScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.GameResultViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.state.GameResultAction

fun NavGraphBuilder.oneRoundWonderNavGraph(
    appModule: AndroidAppModule,
    gameEngine: GameEngine,
    navToHome: () -> Unit
) {

    navigation<OneRoundRootNavRoute>(startDestination = OneRoundHostNavRoute) {
        composable<OneRoundHostNavRoute>{
            val offsetParser = AndroidOffsetParser

            val gameNavController = rememberNavController()
            NavHost(navController = gameNavController, startDestination = GameExampleNavRoute) {

                composable<GameExampleNavRoute> {

                    val viewModel = viewModel<ShowExampleViewModel>(
                        factory = viewModelFactory {
                            initializer {
                                ShowExampleViewModel(
                                    gameEngine = gameEngine,
                                    navToDraw = {
                                        gameNavController.navigate(GameDrawNavRoute)
                                    }
                                )
                            }
                        }
                    )

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        OneRoundWonderTopBar(
                            actionOnClose = {
                                navToHome()
                            }
                        )
                        DrawExampleScreen(
                            uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                        )
                    }
                }

                composable<GameDrawNavRoute> {

                    val careTaker = remember {
                        PathDataCareTaker()
                    }
                    val factory = viewModelFactory {
                        initializer {
                            GameDrawViewModel(
                                careTaker = careTaker,
                                offsetParser = offsetParser,
                                gameEngine = gameEngine
                            )
                        }

                    }
                    val viewModel = viewModel<GameDrawViewModel>(
                        factory = factory
                    )
                    OneRoundWonderScreen(
                        actionOnClose = {
                            navToHome()
                        },
                        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                        onAction = viewModel::handleAction,
                        onDone = {
                            viewModel.onDone()
                            gameNavController.navigate(GameResultNavRoute)
                        }
                    )
                }

                composable<GameResultNavRoute> {

                    val ratingTextGenerator = appModule.ratingTextGenerator
                    val resultCalculator = ResultCalculator

                    val context = LocalContext.current
                    val bitmapPrinter = AndroidBitmapPrinter(context)
                    val viewModel = viewModel<GameResultViewModel>(
                        factory = viewModelFactory {
                            initializer {
                                GameResultViewModel(
                                    ratingTextGenerator = ratingTextGenerator,
                                    resultCalculator = resultCalculator,
                                    bitmapPrinter = bitmapPrinter
                                )
                            }
                        }
                    )

                    val examplePath = gameEngine.getResult().let {
                       it.examplePath.map {
                           coordinatesToPath(it.paths)
                       }
                    }.reduce { acc, path ->
                        acc + path
                    }

                    val userPath = gameEngine.getResult().let {
                        it.userPath.map {
                            coordinatesToPath(it.paths)
                        }
                    }

                    val ratingUi = remember {
                        viewModel.getRatingUi(gameEngine.getResult())
                    }

                    GameResultScreen(
                        examplePath = examplePath,
                        userPath = userPath,
                        ratingUi = ratingUi,
                        onAction = { action ->
                            when (action) {
                                GameResultAction.Close -> {
                                    navToHome()
                                }

                                GameResultAction.TryAgain -> {
                                    gameNavController.popBackStack()
                                    gameNavController.navigate(GameExampleNavRoute) {
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