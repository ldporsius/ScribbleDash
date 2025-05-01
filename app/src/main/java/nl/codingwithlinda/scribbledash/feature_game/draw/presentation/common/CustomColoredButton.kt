package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomColoredButton(
    text: String,
    color: Color,
    borderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 8.dp, color = borderColor)
    ) {
        Text(text = text.uppercase(),
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}