package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasWoodTexture
import nl.codingwithlinda.scribbledash.core.data.util.bitmapFromCanvasProduct
import nl.codingwithlinda.scribbledash.core.data.util.centerPath
import nl.codingwithlinda.scribbledash.core.data.util.combinedPath
import nl.codingwithlinda.scribbledash.core.data.util.toBitmapUiOnly
import nl.codingwithlinda.scribbledash.core.domain.ratings.Oops
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText
import nl.codingwithlinda.scribbledash.core.presentation.util.asString
import nl.codingwithlinda.scribbledash.core.test.testExampleDrawable
import nl.codingwithlinda.scribbledash.core.presentation.design_system.buttons.CustomColoredButton
import nl.codingwithlinda.scribbledash.core.presentation.design_system.components.CoinsEarnedComponent
import nl.codingwithlinda.scribbledash.core.presentation.design_system.components.CounterComponent
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.state.GameResultAction
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.backgroundGradient
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameResultComparison
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.state.OneRoundResultUiState

@Composable
fun GameResultScreen(
    uiState: OneRoundResultUiState,
    reward: Int,
    onAction: (GameResultAction) -> Unit
) {
    val context = LocalContext.current

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

        Text(text = uiState.ratingUi.accuracyPercent.toString() + "%",
            style = MaterialTheme.typography.displayLarge)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            horizontalArrangement = Arrangement.Center


        ) {

            GameResultComparison(
                examplePath = uiState.examplePath,
                userPath = uiState.userPath,
                userBackground = {reqSize ->
                    uiState.userBackgroundCanvas?.let {
                        context.bitmapFromCanvasProduct(it, reqSize )
                    }
                },
                checkIcon = {  }
            )
        }

        Spacer(modifier = Modifier.weight(.5f))
        Text(uiState.ratingUi.title.asString(),
            style = MaterialTheme.typography.headlineLarge)
        Text(
            uiState.ratingUi.text.asString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(0.8f),
            textAlign = TextAlign.Center
        )

        CoinsEarnedComponent(
            coins = reward,
            modifier = Modifier.padding(top = 16.dp)
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
    val uPath = expath.examplePath.map {
        ColoredPath(
            path = it,
            color = android.graphics.Color.BLUE
        )
    }
    val uiState = OneRoundResultUiState(
        examplePath = combinedPath(expath.examplePath),
        userPath = uPath,
        userBackgroundCanvas = CanvasWoodTexture(),
        ratingUi = RatingUi(
            rating = Oops(),
            accuracyPercent = 100,
            title = UiText.DynamicText("Woohoo"),
            text = UiText.StringResource(R.string.feedback_good_1)
        )
    )

    ScribbleDashTheme {
        GameResultScreen(
            uiState,
            reward = 99,
            onAction = {}
        )
    }
}