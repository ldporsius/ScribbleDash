package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.codingwithlinda.scribbledash.core.di.AppModule
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameResultNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.util.ViewModelUtil
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameMainScreen

@Composable
fun GameDrawRoot(
    appModule: AppModule,
    gameEngine: GameEngineTemplate,
    gameNavController: androidx.navigation.NavHostController,
    topBar: @Composable () -> Unit = {}
) {

    val viewModel = ViewModelUtil.createGameDrawViewModel(gameEngine, appModule.shoppingCart){
        gameNavController.popBackStack()
        gameNavController.navigate(GameResultNavRoute)
    }

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

        }
    )
}