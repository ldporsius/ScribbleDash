package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.presentation.design_system.buttons.CloseButton
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameSuccessCounter

@Composable
fun EndlessTopBar(
    numberSuccess: Int,
    actionOnClose: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.size(16.dp))
        GameSuccessCounter(
            successes = numberSuccess.toString()
        )

        CloseButton(
            actionOnClose = { actionOnClose() },
        )
    }
}

