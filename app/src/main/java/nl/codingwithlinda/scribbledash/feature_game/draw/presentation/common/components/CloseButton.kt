package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.ui.theme.onSurface

@Composable
fun CloseButton(
    actionOnClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { actionOnClose() },
        modifier = modifier
            .size(32.dp)
            .border(
                width = 2.dp,
                color = onSurface,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = "close",
            tint = onSurface
        )
    }
}