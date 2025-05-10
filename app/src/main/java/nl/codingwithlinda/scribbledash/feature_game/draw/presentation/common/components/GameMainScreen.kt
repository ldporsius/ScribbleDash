package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.room.util.TableInfo
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawExampleUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.ui.theme.backgroundGradient

@Composable
fun GameMainScreen(
    topBar: @Composable () -> Unit,
    drawState: DrawState,
    exampleUiState: DrawExampleUiState,
    gameDrawUiState: GameDrawUiState,
    onAction: (DrawAction) -> Unit,
    onDone: () -> Unit,
    ) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = backgroundGradient
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            topBar()

            when (drawState) {
                DrawState.EXAMPLE -> {
                    DrawExampleScreen(exampleUiState)
                }

                DrawState.USER_INPUT -> {
                    UserInputScreen(
                        uiState = gameDrawUiState,
                        onAction = onAction,
                        onDone = onDone,
                    )
                }
            }
        }
    }
}