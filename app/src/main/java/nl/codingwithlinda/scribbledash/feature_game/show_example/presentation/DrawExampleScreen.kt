package nl.codingwithlinda.scribbledash.feature_game.show_example.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.feature_game.show_example.presentation.state.DrawExampleUiState
import nl.codingwithlinda.scribbledash.ui.theme.backgroundGradient

@Composable
fun DrawExampleScreen(
    uiState: DrawExampleUiState,
    actionOnClose: () -> Unit,
) {
    val gridColor = MaterialTheme.colorScheme.onSurface
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = backgroundGradient
        )
    ) {
        IconButton(
            onClick = { actionOnClose() },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Ready, set...",
                style = MaterialTheme.typography.displayMedium
            )
            Canvas(modifier = Modifier
                .width(360.dp)
                .aspectRatio(1f)
                .clipToBounds()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))

            ) {
                val width = this.size.width
                val gridSpacing = width / 3

                (1 .. 2).onEach { i ->
                    drawLine(
                        color = gridColor,
                        start = androidx.compose.ui.geometry.Offset(0f, i * gridSpacing),
                        end = androidx.compose.ui.geometry.Offset(this.size.width, i * gridSpacing),
                    )
                    drawLine(
                        color = gridColor,
                        start = androidx.compose.ui.geometry.Offset(i * gridSpacing, 0f),
                        end = androidx.compose.ui.geometry.Offset( i * gridSpacing, this.size.height),
                    )
                }

                uiState.drawPaths.onEach {path ->

                    val color = uiState.pathColor
                    drawPath(
                        path = path.asComposePath(),
                        color = color,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 16.dp)
        ) {
           Text(uiState.counter.toString())
        }
    }
}