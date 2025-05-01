package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawNavRoute
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.SpeedDrawScreen

fun NavGraphBuilder.speedDrawNavGraph(
    gameNavController: NavHostController,
    appModule: AndroidAppModule,
    navToHome: () -> Unit
) {

    composable<SpeedDrawNavRoute> {


      Text("SpeedDrawScreen")

    }
}