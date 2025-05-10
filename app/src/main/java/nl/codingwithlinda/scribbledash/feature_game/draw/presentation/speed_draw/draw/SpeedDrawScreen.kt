package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameDrawBottomBar
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.UserDrawCanvas
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.DrawExampleScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawExampleUiState

@Composable
fun SpeedDrawScreen(
    topBarUiState: SpeedDrawUiState,
    exampleUiState: DrawExampleUiState,
    gameDrawUiState: GameDrawUiState,
    onAction: (DrawAction) -> Unit,
    onDone: () -> Unit,
    actionOnClose: () -> Unit,
    ) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SpeedDrawTopBar(
            uiState = topBarUiState,
            actionOnClose = actionOnClose,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        )

        AnimatedContent(topBarUiState.drawState, label = "") { drawState ->
            when(drawState){
                DrawState.EXAMPLE -> {
                    DrawExampleScreen(
                        uiState = exampleUiState,
                    )
                }
                DrawState.USER_INPUT -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Time to draw!",
                            style = MaterialTheme.typography.displayMedium
                        )
                        UserDrawCanvas(
                            uiState = gameDrawUiState,
                            onAction = onAction
                        )

                        Spacer(modifier = Modifier.weight(1f))
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                        ) {
                            GameDrawBottomBar(
                                uiState = gameDrawUiState,
                                onAction = onAction,
                                onDone = onDone,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

    }
}