package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import nl.codingwithlinda.scribbledash.core.di.AppModule
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.SpeedDrawResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.util.ViewModelUtil
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameMainScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.SpeedDrawTopBar
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.SpeedDrawViewModel
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.result.SpeedDrawResultScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.result.SpeedDrawResultViewModel

fun NavGraphBuilder.speedDrawNavGraph(
    gameNavController: NavHostController,
    appModule: AppModule,
    gameEngine: GameEngineTemplate,
    navToHome: () -> Unit
) {

    composable<SpeedDrawNavRoute> {

        val speedDrawViewModel = viewModel<SpeedDrawViewModel>(
            factory = viewModelFactory {
                initializer {
                    SpeedDrawViewModel(
                        gameEngine = gameEngine,
                            navToResult = {
                                gameNavController.popBackStack()
                                gameNavController.navigate(SpeedDrawResultNavRoute)
                            }
                        )
                }
            }
        )
       

        val gameDrawViewModel = ViewModelUtil.createGameDrawViewModel(gameEngine)

        GameMainScreen(
            topBar = {
                SpeedDrawTopBar(
                    uiState = speedDrawViewModel.topBarUiState.collectAsStateWithLifecycle().value,
                    actionOnClose = {
                        speedDrawViewModel.stopCountdown()
                        navToHome()
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            },
            drawState = gameDrawViewModel.drawState.collectAsStateWithLifecycle().value,
            exampleUiState = gameDrawViewModel.exampleUiState.collectAsStateWithLifecycle().value,
            gameDrawUiState = gameDrawViewModel.uiState.collectAsStateWithLifecycle().value,
            onAction = gameDrawViewModel::handleAction,
            onDone = {
                gameDrawViewModel.onDone()
                gameDrawViewModel.handleAction(DrawAction.Clear)
                speedDrawViewModel.onDone()
            }
        )

    }

    composable<SpeedDrawResultNavRoute> {

        val viewModel = viewModel<SpeedDrawResultViewModel>(
            factory = viewModelFactory {
                initializer {
                    SpeedDrawResultViewModel(
                        ratingMapper = RatingMapper(appModule.ratingTextGenerator),
                        gameEngine = gameEngine
                    )
                }
            }
        )
        SpeedDrawResultScreen(
            uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
            onClose = navToHome,
            onDrawAgain = {
                gameNavController.popBackStack()
                gameNavController.navigate(SpeedDrawNavRoute)
            }
        )
    }
}