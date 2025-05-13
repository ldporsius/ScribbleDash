package nl.codingwithlinda.scribbledash.core.presentation.design_system.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onSurface

@Composable
fun CloseButton(
    actionOnClose: () -> Unit,
) {
    IconButton(
        onClick = { actionOnClose() },
        modifier = Modifier
            .size(48.dp)
            .border(
                width = 2.dp,
                color = onSurface,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = "close",
            modifier = Modifier.size(24.dp),
            tint = onSurface
        )
    }
}