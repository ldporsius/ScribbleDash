package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameLevelNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameRootNavRoute
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

                val viewModel = viewModel<GameDrawViewModel>()
                GameDrawScreen(
                    actionOnClose = {
                        navToHome()
                    },
                    uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                    onAction = viewModel::handleAction
                )
            }
        }
    }
}