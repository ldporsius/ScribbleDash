package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

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
import androidx.navigation.navigation
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessGameOverNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessHostNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessRootNavRoute
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.EndlessDrawScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.EndlessDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.game_over.EndlessGameOverScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.game_over.EndlessGameOverViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.result.EndlessModeResultScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.result.EndlessResultViewModel


fun NavGraphBuilder.endlessModeNavGraph(
    appModule: AndroidAppModule,
    onNavHome: () -> Unit
){

    navigation<EndlessRootNavRoute>( startDestination = EndlessHostNavRoute){
        composable<EndlessHostNavRoute>{
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = EndlessDrawNavRoute){
                composable< EndlessDrawNavRoute>{
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
                            )
                        }

                    }
                    val gameDrawViewModel = viewModel<GameDrawViewModel>(
                        factory = factory
                    )

                    val endlessDrawViewModel = viewModel<EndlessDrawViewModel>(
                        factory = viewModelFactory {
                            initializer {
                                EndlessDrawViewModel(
                                    exampleProvider = appModule.drawExampleProvider,
                                    gamesManager = appModule.gamesManager
                                )
                            }
                        }
                    )
                    EndlessDrawScreen(
                        topBarUiState = endlessDrawViewModel.endlessUiState.collectAsStateWithLifecycle().value,
                        exampleUiState = endlessDrawViewModel.exampleUiState.collectAsStateWithLifecycle().value,
                        gameDrawUiState = gameDrawViewModel.uiState.collectAsStateWithLifecycle().value,
                        onAction = gameDrawViewModel::handleAction,
                        onDone = {
                            endlessDrawViewModel.onDone()
                            navController.navigate(EndlessResultNavRoute)
                        },
                        actionOnClose = {
                            navController.popBackStack()
                            onNavHome()
                        }
                    )
                }

                composable<EndlessResultNavRoute> {

                    val viewModel = viewModel<EndlessResultViewModel>(
                        factory = viewModelFactory {
                            initializer {
                                EndlessResultViewModel(
                                    gamesManager = appModule.gamesManager,
                                    ratingMapper = RatingMapper(appModule.ratingTextGenerator)
                                )
                            }
                        }
                    )
                    EndlessModeResultScreen(
                        endlessResultUiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                        onClose = {
                            navController.popBackStack()
                            onNavHome()
                        },
                        onFinish = {
                            navController.navigate(EndlessGameOverNavRoute){
                                popUpTo(EndlessDrawNavRoute){
                                    inclusive = true
                                }
                            }
                        },
                        onNext = {
                           navController.navigate(EndlessDrawNavRoute){
                               popUpTo(EndlessDrawNavRoute){
                                   inclusive = true
                               }
                           }
                        }
                    )
                }

                composable<EndlessGameOverNavRoute> {

                    val viewModel = viewModel<EndlessGameOverViewModel>(
                        factory = viewModelFactory {
                            initializer {
                                EndlessGameOverViewModel(
                                    gamesManager = appModule.gamesManager,
                                    ratingMapper = RatingMapper(appModule.ratingTextGenerator)
                                )
                            }
                        }
                    )

                    EndlessGameOverScreen(
                        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                        onClose = onNavHome,
                        onDrawAgain = {
                            viewModel.startNewGame()
                            navController.navigate(EndlessDrawNavRoute){
                                popUpTo(EndlessDrawNavRoute){
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

            }
        }


    }
}