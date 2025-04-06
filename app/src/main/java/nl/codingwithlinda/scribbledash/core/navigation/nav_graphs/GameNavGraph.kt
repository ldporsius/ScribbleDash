package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameLevelNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameRootNavRoute
import nl.codingwithlinda.scribbledash.feature_game.presentation.GameLevelScreen


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
                Column {
                    Text(text = "Game draw")
                    Button(onClick = {
                        gameNavController.navigate(GameLevelNavRoute)
                    }
                    ) {
                        Text(text = "Level")
                    }
                }
            }
        }
    }
}