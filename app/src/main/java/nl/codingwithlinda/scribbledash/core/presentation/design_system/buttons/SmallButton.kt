package nl.codingwithlinda.scribbledash.core.presentation.design_system.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SmallButton(
    icon: Int,
    iconTint: () -> Color,
    buttonColor: () -> Color,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {

    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
            .background(
                color = buttonColor(),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = iconTint()
        )
    }
}