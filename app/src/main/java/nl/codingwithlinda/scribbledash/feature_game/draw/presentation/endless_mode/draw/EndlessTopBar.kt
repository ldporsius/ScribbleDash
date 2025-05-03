package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.CloseButton
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameSuccessCounter
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.state.EndlessUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.SpeedDrawCounter
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.draw.state.SpeedDrawUiState
import nl.codingwithlinda.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun SpeedDrawTopBar(
    uiState: EndlessUiState,
    actionOnClose: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.size(16.dp))
        GameSuccessCounter(
            successes = uiState.numberSuccess.toString()
        )

        CloseButton(
            actionOnClose = { actionOnClose() },
        )
    }
}

