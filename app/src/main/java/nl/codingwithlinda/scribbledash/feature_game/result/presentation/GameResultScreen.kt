package nl.codingwithlinda.scribbledash.feature_game.result.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.centerPath
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawable
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.components.CustomColoredButton
import nl.codingwithlinda.scribbledash.feature_game.result.presentation.state.GameResultAction
import nl.codingwithlinda.scribbledash.ui.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.ui.theme.backgroundGradient

@Composable
fun GameResultScreen(
    result: DrawResult,
    onAction: (GameResultAction) -> Unit
) {


    val examplePath = result.examplePath.path.asComposePath()

    val resultBoundsE = examplePath.getBounds()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    onAction(GameResultAction.Close)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close"
                )
            }
        }

        Text(text = "100%",
            style = MaterialTheme.typography.displayLarge)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(width = 2.dp, color = Color.Black)

        ) {

            Column(
                modifier = Modifier
                    .height(200.dp)
                    .weight(1f)
                    .onSizeChanged {
                        println("SIZE OF EXAMPLE COLUMN: $it")
                        println("SIZE OF EXAMPLE BOUNDS, WIDTH, HEIGHT: ${resultBoundsE.width}, ${resultBoundsE.height}")
                        val sx = it.width.toFloat() / resultBoundsE.width
                        val sy = it.height.toFloat() / resultBoundsE.height
                        println("SX: $sx, SY: $sy")

                    }
                    .graphicsLayer {
                        this.rotationZ = -0f
                    }
            ) {
                Text(text = "Example")
                OutlinedCard(
                    elevation = CardDefaults.outlinedCardElevation(
                        defaultElevation = 8.dp
                    )
                ) {

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        centerPath(examplePath.asAndroidPath())
                        drawPath(
                            path = examplePath,
                            color = Color.Black,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .height(200.dp)
                    .weight(1f)
            ) {

                OutlinedCard {
                    Text(text = "Drawing")
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        val userPath = result.userPath.path.asComposePath()
                        centerPath(userPath.asAndroidPath())
                        drawPath(
                            path = userPath,
                            color = Color.Black,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        CustomColoredButton(
            text = "Try again",
            onClick = {
                onAction(GameResultAction.TryAgain)
            },
            color = Color.Blue,
            borderColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        )

    }
}

@Preview
@Composable
private fun previewGameResultScreen() {
    val context = LocalContext.current
    val dRes = R.drawable.alien
    val expath = testExampleDrawable(context, dRes)
    ScribbleDashTheme {
        GameResultScreen(
            result = DrawResult(
                id = "",
                examplePath = expath,
                userPath = expath
            )
        ) { }
    }
}