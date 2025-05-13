package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.scribbledash.core.presentation.design_system.buttons.CloseButton
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameSuccessCounter
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.ScribbleDashTheme

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
            timeLeft = uiState.timeLeft(),
            textColor = uiState.timeLeftColor()
        )

        GameSuccessCounter(
            successes = uiState.successes.toString()
        )

        CloseButton(
            actionOnClose = { actionOnClose() },
        )
    }
}

@Preview
@Composable
private fun SpeedDrawTopBarPreview() {

    val uiState = SpeedDrawUiState(
       timeLeftSeconds = 120
    )
    ScribbleDashTheme {
        SpeedDrawTopBar(
            uiState = uiState,
            actionOnClose = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}