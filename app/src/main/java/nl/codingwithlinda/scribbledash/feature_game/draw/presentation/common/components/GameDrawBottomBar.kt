package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.backgroundDark
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onBackground
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.success
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.surfaceHigh
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.surfaceLow
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.surfaceLowest

@Composable
fun GameDrawBottomBar(
    uiState: GameDrawUiState,
    onAction: (DrawAction) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = androidx.compose.ui.Alignment.CenterHorizontally),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        SmallButton(
            icon = R.drawable.reply,
            iconTint = {
                if (uiState.isUndoAvailable()) onBackground else onBackground.copy(alpha = 0.5f)
            },
            buttonColor = {
                if (uiState.isUndoAvailable()) backgroundDark else surfaceLow
            },
            text = "Undo",
            onClick = { onAction(DrawAction.Undo) }
        )
        SmallButton(
            icon = R.drawable.forward,
            iconTint = {
                if (uiState.isRedoAvailable()) onBackground else onBackground.copy(alpha = 0.5f)
            },
            buttonColor = {
                if (uiState.isRedoAvailable()) backgroundDark else surfaceLow
            },
            text = "Redo",
            onClick = { onAction(DrawAction.Redo) }
        )

        val clearButtonColor = if (uiState.isClearAvailable()) success else surfaceLowest
        CustomColoredButton(
            text = "Done",
            color = clearButtonColor,
            borderColor = surfaceHigh,
            onClick = { onDone() }
        )
    }
}