package nl.codingwithlinda.scribbledash.feature_game.draw.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.components.GameDrawBottomBar
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.ui.theme.backgroundGradient

@Composable
fun GameDrawScreen(
    offsetParser: OffsetParser<AndroidDrawPath>,
    pathDrawer: PathDrawer<AndroidDrawPath>,
    uiState: GameDrawUiState,
    onAction: (DrawAction) -> Unit,
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
                text = "Time to draw!",
                style = MaterialTheme.typography.displayMedium
            )
           Canvas(modifier = Modifier
               .width(360.dp)
               .aspectRatio(1f)
               .clipToBounds()
               .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
               .pointerInput(true){
                   this.detectDragGestures(
                       onDragStart = {
                           onAction(DrawAction.StartPath(it))
                       },
                       onDragEnd = {
                           onAction(DrawAction.Save)
                       }
                   ) { change, dragAmount ->
                       //println("CHANGE: ${change.position}")
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

               uiState.drawPaths.onEach {
                   val path = offsetParser.parseOffset(pathDrawer, it)
                   val color = Color(it.color)
                   drawPath(
                       path = path.path.asComposePath(),
                       color = color,
                       style = Stroke(width = 2.dp.toPx())
                   )
               }
               uiState.currentPath?.let {
                   val path = offsetParser.parseOffset(pathDrawer, it)
                   val color = Color(it.color)
                   drawPath(
                       path = path.path.asComposePath(),
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
            GameDrawBottomBar(
                uiState = uiState,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}