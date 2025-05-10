package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameDrawBottomBar
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.UserDrawCanvas
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.DrawExampleScreen
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.example.presentation.state.DrawExampleUiState
import nl.codingwithlinda.scribbledash.ui.theme.backgroundGradient

@Composable
fun OneRoundWonderScreen(
    drawState: DrawState,
    exampleUiState: DrawExampleUiState,
    uiState: GameDrawUiState,
    onAction: (DrawAction) -> Unit,
    onDone: () -> Unit,
    actionOnClose: () -> Unit,
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = backgroundGradient
        )
    ) {
        OneRoundWonderTopBar(
            actionOnClose = actionOnClose,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        when(drawState){
            DrawState.EXAMPLE -> {
                DrawExampleScreen(exampleUiState)
            }
            DrawState.USER_INPUT -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Time to draw!",
                        style = MaterialTheme.typography.displayMedium
                    )

                    UserDrawCanvas(
                        uiState = uiState,
                        onAction = onAction
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                    ) {
                        GameDrawBottomBar(
                            uiState = uiState,
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