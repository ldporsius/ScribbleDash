package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.components

import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.data.util.centerPath
import nl.codingwithlinda.scribbledash.core.data.util.toBitmapUiOnly

@Composable
fun GameResultComparison(
    examplePath: Path,
    userPath: List<Path>,
    modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .width(125.dp)
            .graphicsLayer {
                this.rotationZ = -10f
            }
            .padding(top = 16.dp, end = 8.dp)

        ,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Example",
            style = MaterialTheme.typography.bodySmall)
        OutlinedCard(
            modifier = Modifier.shadow(
                8.dp
            )
        ) {

            Canvas(
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(1f)
            ){
                centerPath(examplePath)
                drawPath(
                    path = examplePath.asComposePath(),
                    color = Color.Black,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 48.dp, start = 8.dp)
            .width(126.dp)
            .graphicsLayer {
                this.rotationZ = 15f
            }

        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Drawing",
            style = MaterialTheme.typography.bodySmall
        )
        OutlinedCard(
            modifier = Modifier.shadow(
                8.dp
            )
        ) {

            Canvas(
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(1f)
            ) {

                val w = size.width
                val h = size.height
                val requiredSize = 100.dp.toPx().toInt()
                val strokeWidth = 4.dp.toPx()
                val bm = userPath.
                toBitmapUiOnly(
                    requiredSize = requiredSize,
                    basisStrokeWidth = strokeWidth
                )
                val dx = w/2 - bm.width/2
                val dy = h/2 - bm.height/2
                drawImage(
                    bm.asImageBitmap(),
                    topLeft = Offset(dx, dy),
                    style = Stroke(2.dp.toPx())
                )
            }
        }
    }
}