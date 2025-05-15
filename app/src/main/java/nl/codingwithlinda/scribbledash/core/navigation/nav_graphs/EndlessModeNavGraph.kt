package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.navigation
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import nl.codingwithlinda.scribbledash.core.di.AppModule
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessGameOverNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessHostNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessRootNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.util.ViewModelUtil
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameMainScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.EndlessDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.EndlessTopBar
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.game_over.EndlessGameOverScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.game_over.EndlessGameOverViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.result.EndlessModeResultScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.result.EndlessResultViewModel


fun NavGraphBuilder.endlessModeNavGraph(
    appModule: AppModule,
    onNavHome: () -> Unit,
    gameEngine: GameEngineTemplate
){

    navigation<EndlessRootNavRoute>( startDestination = EndlessHostNavRoute){
        composable<EndlessHostNavRoute>{
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = EndlessDrawNavRoute){
                composable< EndlessDrawNavRoute>{
                    val gameDrawViewModel = ViewModelUtil.createGameDrawViewModel(gameEngine = gameEngine)

                    val endlessDrawViewModel = viewModel<EndlessDrawViewModel>(
                        factory = viewModelFactory {
                            initializer {
                                EndlessDrawViewModel(
                                    gameEngine = gameEngine
                                )
                            }
                        }
                    )

                    GameMainScreen(
                        topBar = {
                            EndlessTopBar(
                                numberSuccess = endlessDrawViewModel.endlessUiState.collectAsStateWithLifecycle().value.numberSuccess,
                                actionOnClose = {
                                    navController.popBackStack()
                                    onNavHome()
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        drawState = gameDrawViewModel.drawState.collectAsStateWithLifecycle().value,
                        exampleUiState = gameDrawViewModel.exampleUiState.collectAsStateWithLifecycle().value,
                        gameDrawUiState = gameDrawViewModel.uiState.collectAsStateWithLifecycle().value,
                        onAction = gameDrawViewModel::handleAction,
                        onDone = {
                            gameDrawViewModel.onDone()
                            gameDrawViewModel.handleAction(DrawAction.Clear)
                            navController.navigate(EndlessResultNavRoute)
                        },
                    )
                }

                composable<EndlessResultNavRoute> {

                    val bmPrinter = AndroidBitmapPrinter(LocalContext.current)
                    val viewModel = viewModel<EndlessResultViewModel>(
                        factory = viewModelFactory {
                            initializer {
                                EndlessResultViewModel(
                                    gameEngine = gameEngine,
                                    ratingMapper = RatingMapper(appModule.ratingTextGenerator),
                                    bmPrinter = bmPrinter
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
                                    gameEngine = gameEngine,
                                    ratingMapper = RatingMapper(appModule.ratingTextGenerator)
                                )
                            }
                        }
                    )

                    EndlessGameOverScreen(
                        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                        onClose ={
                            navController.popBackStack()
                            onNavHome()
                        },
                        onDrawAgain = {
                            navController.popBackStack()
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