package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.UserDrawCanvas
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.DrawExampleScreen
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.state.DrawExampleUiState

@Composable
fun SpeedDrawScreen(
    topBarUiState: SpeedDrawUiState,
    exampleUiState: DrawExampleUiState,
    gameDrawUiState: GameDrawUiState,
    onAction: (DrawAction) -> Unit,
    actionOnClose: () -> Unit,
    modifier: Modifier = Modifier) {

    Column {
        SpeedDrawTopBar(
            uiState = topBarUiState,
            actionOnClose = actionOnClose,
            modifier = modifier
        )

        AnimatedContent(topBarUiState.drawState, label = "") { drawState ->
            when(drawState){
                DrawState.EXAMPLE -> {
                    DrawExampleScreen(
                        uiState = exampleUiState,
                    )
                }
                DrawState.USER_INPUT -> {
                    UserDrawCanvas(
                        uiState = gameDrawUiState,
                        onAction = onAction
                    )
                }
            }
        }

    }
}