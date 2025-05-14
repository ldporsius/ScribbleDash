package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onBackground
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onSurface

@Composable
fun UserDrawCanvas(
    uiState: GameDrawUiState,
    onAction: (DrawAction) -> Unit
) {

    val gridColor: Color = onSurface
    val pathColor: Color = onBackground

    Canvas(modifier = Modifier
        .semantics {
            contentDescription = "canvas"
        }
        .width(360.dp)
        .aspectRatio(1f)
        .clipToBounds()
        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
        .pointerInput(Unit){
            this.detectDragGestures(
                onDragStart = {
                    println("START: $it")
                    onAction(DrawAction.StartPath(it))
                },
                onDragEnd = {
                    println("END: ")
                    onAction(DrawAction.Save)
                }
            ) { change, dragAmount ->
                println("CHANGE: ${change.position}")
                //println("DRAG AMOUNT: $dragAmount")
                onAction(DrawAction.Draw(change.position))
            }
        }
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
            drawPath(
                path = path,
                color = pathColor,
                style = Stroke(width = 2.dp.toPx())
            )
        }
        uiState.currentPath?.let { path ->
            val color = pathColor
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}