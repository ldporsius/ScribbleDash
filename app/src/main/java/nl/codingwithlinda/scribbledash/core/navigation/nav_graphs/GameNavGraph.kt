package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameLevelNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameRootNavRoute
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.GameDrawScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.level.presentation.GameLevelScreen


fun NavGraphBuilder.GameNavGraph(
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
                    actionOnLevel = {
                        gameNavController.navigate(GameDrawNavRoute)
                    }
                )
            }

            composable<GameDrawNavRoute> {

                val pathDrawer = StraightPathDrawer()
                val offsetParser = AndroidOffsetParser
                val careTaker = remember{
                    PathDataCareTaker()
                }
                val factory = viewModelFactory {
                    initializer {
                        GameDrawViewModel(
                            careTaker = careTaker
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
        }
    }
}