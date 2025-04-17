package nl.codingwithlinda.scribbledash.feature_game.result.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.centerPath
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.toBitmap
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
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

    var originalWidth by remember {
        mutableStateOf(0f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                originalWidth = it.width.toFloat() - 2 * 16.dp.value
            }
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
                .padding(8.dp)
            ,
            horizontalArrangement = Arrangement.Center


        ) {

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
                    val bm = result.examplePath.toBitmap(100)

                    Canvas(
                        modifier = Modifier
                            .width(120.dp)
                            .aspectRatio(1f)
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
                        val strokeWidth = 2.dp.toPx()
                        val bm = result.userPath.toBitmap(requiredSize, strokeWidth)
                        val dx = w/2 - bm.width/2
                        val dy = h/2 - bm.height/2
                        drawImage(
                            bm.asImageBitmap(),
                            topLeft = Offset(dx, dy),
                            style = Stroke(1.dp.toPx())
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(.5f))
        Text("Woohoo!",
            style = MaterialTheme.typography.headlineLarge)
        Text(
            stringResource(R.string.feedback_woohoo_1),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(0.8f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

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
private fun PreviewGameResultScreen() {
    val context = LocalContext.current
    val dRes = R.drawable.alien
    val expath = testExampleDrawable(context, dRes)
    ScribbleDashTheme {
        GameResultScreen(
            result = DrawResult(
                id = "",
                level = GameLevel.MASTER,
                examplePath = expath,
                userPath = listOf( expath)
            )
        ) { }
    }
}