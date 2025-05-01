package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawNavRoute
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.SpeedDrawScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.SpeedDrawViewModel

fun NavGraphBuilder.speedDrawNavGraph(
    gameNavController: NavHostController,
    appModule: AndroidAppModule,
    navToHome: () -> Unit
) {

    composable<SpeedDrawNavRoute> {

        val viewModel = viewModel<SpeedDrawViewModel>()
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
        val gameDrawViewModel = viewModel<GameDrawViewModel>(
            factory = factory
        )
      SpeedDrawScreen(
          topBarUiState = viewModel.topBarUiState.collectAsStateWithLifecycle().value,
          exampleUiState = viewModel.exampleUiState.collectAsStateWithLifecycle().value,
          gameDrawUiState = gameDrawViewModel.uiState.collectAsStateWithLifecycle().value,
          onAction = gameDrawViewModel::handleAction,
          actionOnClose = navToHome,
          modifier = Modifier
      )

    }
}