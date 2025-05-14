package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.util.ViewModelUtil
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameMainScreen

@Composable
fun GameDrawRoot(
    gameEngine: GameEngineTemplate,
    gameNavController: androidx.navigation.NavHostController,
    topBar: @Composable () -> Unit = {}
) {

    val viewModel = ViewModelUtil.createGameDrawViewModel(gameEngine)

    GameMainScreen(
        topBar = {
           topBar()
        },
        drawState = viewModel.drawState.collectAsStateWithLifecycle().value,
        exampleUiState = viewModel.exampleUiState.collectAsStateWithLifecycle().value,
        gameDrawUiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        onAction = viewModel::handleAction,
        onDone = {
            viewModel.onDone()
            gameNavController.popBackStack()
            gameNavController.navigate(GameResultNavRoute)
        }
    )
}