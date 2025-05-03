package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawResultNavRoute
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.SpeedDrawScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.SpeedDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.result.SpeedDrawResultScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.result.SpeedDrawResultViewModel

fun NavGraphBuilder.speedDrawNavGraph(
    gameNavController: NavHostController,
    appModule: AndroidAppModule,
    navToHome: () -> Unit
) {

    composable<SpeedDrawNavRoute> {

        val context = LocalContext.current
        val bitmapPrinter = remember {
            AndroidBitmapPrinter(context)
        }
        val viewModel = viewModel<SpeedDrawViewModel>(
            factory = viewModelFactory {
                initializer {
                    SpeedDrawViewModel(
                        exampleProvider = appModule.drawExampleProvider,
                        resultCalculator = ResultCalculator,
                            bitmapPrinter = bitmapPrinter,
                            gamesManager = appModule.gamesManager,
                            navToResult = {
                                gameNavController.navigate(SpeedDrawResultNavRoute){
                                    popUpTo(SpeedDrawNavRoute){
                                        inclusive = true
                                    }
                                }
                            }
                        )
                }
            }
        )
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
      SpeedDrawScreen(
          topBarUiState = viewModel.topBarUiState.collectAsStateWithLifecycle().value,
          exampleUiState = viewModel.exampleUiState.collectAsStateWithLifecycle().value,
          gameDrawUiState = gameDrawViewModel.uiState.collectAsStateWithLifecycle().value,
          onAction = gameDrawViewModel::handleAction,
          onDone = {
              gameDrawViewModel.onDone()
              gameDrawViewModel.handleAction(DrawAction.Clear)

              viewModel.onDone()
          },
          actionOnClose = navToHome,

      )

    }

    composable<SpeedDrawResultNavRoute> {

        val viewModel = viewModel<SpeedDrawResultViewModel>(
            factory = viewModelFactory {
                initializer {
                    SpeedDrawResultViewModel(
                        ratingMapper = RatingMapper(appModule.ratingTextGenerator),
                        gamesManager = appModule.gamesManager
                    )
                }
            }
        )
        SpeedDrawResultScreen(
            uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
            onClose = navToHome,
            onDrawAgain = {
                viewModel.startNewGame()
                gameNavController.navigate(SpeedDrawNavRoute) {
                    popUpTo(SpeedDrawResultNavRoute) {
                        inclusive = true
                    }
                }
            }
        )
    }
}