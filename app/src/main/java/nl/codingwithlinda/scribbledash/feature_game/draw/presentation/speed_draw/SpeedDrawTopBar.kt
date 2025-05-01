package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.CloseButton
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameSuccessCounter
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun SpeedDrawTopBar(
    uiState: SpeedDrawUiState,
    actionOnClose: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SpeedDrawCounter(
            timeLeft = uiState.timeLeft
        )

        GameSuccessCounter(
            successes = uiState.successes
        )

        CloseButton(
            actionOnClose = { actionOnClose() },
            modifier = Modifier.size(48.dp)
        )
    }
}

@Preview
@Composable
private fun SpeedDrawTopBarPreview() {

    val uiState = SpeedDrawUiState(
        timeLeft = "2:00"
    )
    ScribbleDashTheme {
        SpeedDrawTopBar(
            uiState = uiState,
            actionOnClose = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}