package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameExampleNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameLevelNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameRootNavRoute
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.GameDrawScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.level.presentation.GameLevelScreen
import nl.codingwithlinda.scribbledash.feature_game.result.presentation.GameResultScreen
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
                    actionOnLevel = {level ->
                        ResultManager.INSTANCE.addResult(
                            level = level
                        )
                        gameNavController.navigate(GameExampleNavRoute)
                    }
                )
            }

            composable<GameExampleNavRoute> {

                val viewModel = viewModel<ShowExampleViewModel>(
                    factory = viewModelFactory {
                        initializer {
                            ShowExampleViewModel(
                                exampleProvider = appModule.drawExampleProvider,
                                navToDraw = {
                                    gameNavController.navigate(GameDrawNavRoute)
                                }
                            )
                        }
                    }
                )

                DrawExampleScreen(
                    uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                    actionOnClose = {
                        navToHome()
                    }
                )
            }

            composable<GameDrawNavRoute> {

                val pathDrawer = StraightPathDrawer()
                val offsetParser = AndroidOffsetParser
                val careTaker = remember {
                    PathDataCareTaker()
                }
                val factory = viewModelFactory {
                    initializer {
                        GameDrawViewModel(
                            careTaker = careTaker,
                            offsetParser = offsetParser,
                            pathDrawer = pathDrawer,
                            navToResult = {
                                gameNavController.navigate(GameResultNavRoute)
                            }
                        )
                    }

                }
                val viewModel = viewModel<GameDrawViewModel>(
                    factory = factory
                )
                GameDrawScreen(
                    actionOnClose = {
                        navToHome()
                    },
                    uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                    onAction = viewModel::handleAction,
                    pathDrawer = pathDrawer,
                    offsetParser = offsetParser
                )
            }

            composable<GameResultNavRoute> {
                val hasResult = ResultManager.INSTANCE.getLastResult()
                AnimatedContent(
                    targetState = hasResult,
                    label = "hasResult"
                ) {
                    if (it != null) {
                        ResultManager.INSTANCE.getLastResult()?.let { it1 ->
                            GameResultScreen(
                                result = it1,
                                onAction = {action ->
                                    when(action){
                                        GameResultAction.Close -> {
                                            navToHome()
                                        }
                                        GameResultAction.TryAgain -> {
                                            gameNavController.navigate(GameLevelNavRoute)
                                        }
                                    }
                                }
                            )
                        }
                    } else {
                        Text(text = "No result")
                    }
                }
            }
        }

    }
}