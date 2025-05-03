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
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessHostNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.EndlessRootNavRoute
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.EndlessDrawScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.EndlessDrawViewModel


fun NavGraphBuilder.endlessModeNavGraph(
    appModule: AndroidAppModule
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
                                    exampleProvider = appModule.drawExampleProvider
                                )
                            }
                        }
                    )
                    EndlessDrawScreen(
                        topBarUiState = endlessDrawViewModel.endlessUiState.collectAsStateWithLifecycle().value,
                        exampleUiState = endlessDrawViewModel.exampleUiState.collectAsStateWithLifecycle().value,
                        gameDrawUiState = gameDrawViewModel.uiState.value,
                        onAction = gameDrawViewModel::handleAction,
                        onDone = {},
                        actionOnClose = {}
                    )
                }
            }

        }

    }
}