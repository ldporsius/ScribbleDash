package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation

import android.graphics.Path
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.centerPath
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.combinedPath
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.toBitmapUiOnly
import nl.codingwithlinda.scribbledash.core.domain.model.AndroidDrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.ratings.Oops
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText
import nl.codingwithlinda.scribbledash.core.presentation.util.asString
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawable
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawableMultiPath
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.CustomColoredButton
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.state.GameResultAction
import nl.codingwithlinda.scribbledash.ui.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.ui.theme.backgroundGradient

@Composable
fun GameResultScreen(
    examplePath: Path,
    userPath: List<Path>,
    ratingUi: RatingUi,
    onAction: (GameResultAction) -> Unit
) {

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

        Text(text = ratingUi.accuracyPercent.toString() + "%",
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
                        val strokeWidth = 5.dp.toPx()
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
                            style = Stroke(4.dp.toPx())
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(.5f))
        Text(ratingUi.title.asString(),
            style = MaterialTheme.typography.headlineLarge)
        Text(
            ratingUi.text.asString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(0.8f),
            textAlign = TextAlign.Center
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
            examplePath = expath.androidPath,
            userPath = listOf( expath.androidPath),
            ratingUi = RatingUi(
                rating = Oops(),
                accuracyPercent = 100,
                title = UiText.DynamicText("Woohoo"),
                text = UiText.StringResource(R.string.feedback_good_1)
            ) ,
            onAction = {}
        )
    }
}