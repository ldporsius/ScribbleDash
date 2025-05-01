package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SpeedDrawScreen(
    uiState: SpeedDrawUiState,
    actionOnClose: () -> Unit,
    modifier: Modifier = Modifier) {

    Column {
        SpeedDrawTopBar(
            uiState = uiState,
            actionOnClose = actionOnClose,
            modifier = modifier
        )

    }
}