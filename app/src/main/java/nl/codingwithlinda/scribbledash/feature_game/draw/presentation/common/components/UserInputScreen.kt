package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState

@Composable
fun UserInputScreen(
    uiState: GameDrawUiState,
    onAction: (DrawAction) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
            modifier
            .fillMaxWidth()
            ,
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