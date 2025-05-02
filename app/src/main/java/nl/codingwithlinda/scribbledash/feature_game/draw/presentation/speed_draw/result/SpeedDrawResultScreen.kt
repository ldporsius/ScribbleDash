package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.ratings.Meh
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText
import nl.codingwithlinda.scribbledash.core.presentation.util.asString
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.CloseButton
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.CustomColoredButton
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameSuccessCounter
import nl.codingwithlinda.scribbledash.ui.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.ui.theme.backgroundGradient
import nl.codingwithlinda.scribbledash.ui.theme.onBackground
import nl.codingwithlinda.scribbledash.ui.theme.onSurface
import nl.codingwithlinda.scribbledash.ui.theme.primary
import nl.codingwithlinda.scribbledash.ui.theme.surfaceHigh
import nl.codingwithlinda.scribbledash.ui.theme.surfaceLow

@Composable
fun SpeedDrawResultScreen(
    uiState: SpeedDrawResultUiState,
    onClose: () -> Unit
) {

    uiState.ratingUi ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
            .padding(16.dp)
        ,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,
            horizontalArrangement = Arrangement.End
        ) {
            CloseButton(
                actionOnClose = onClose
            )
        }

        Text("Time's up!")


        ConstraintLayout {
            val (image, anchor) = createRefs()
            val guidline = createGuidelineFromTop(16.dp)


            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                ,
                colors = CardDefaults.elevatedCardColors().copy(
                    containerColor = surfaceHigh
                )

            ) {

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.elevatedCardColors().copy(
                        containerColor = surfaceLow
                    )


                ) {


                    Text(
                        uiState.ratingUi.accuracyPercent.toString() + "%",
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }





                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        uiState.ratingUi.title.asString(),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        uiState.ratingUi.text.asString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 48.dp, vertical = 8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    GameSuccessCounter(
                        successes = uiState.successCount.toString(),
                        backgroundColor = uiState.backgroundColor(),
                        foregroundColor = if (uiState.isHighestNumberOfSuccesses) surfaceHigh else onBackground
                    )

                    if (uiState.isHighestNumberOfSuccesses){
                        Text(
                            "NEW HIGH!",
                            style = MaterialTheme.typography.labelSmall,
                            color = onSurface,
                            modifier = Modifier
                        )

                    }
                }
            }

            if (uiState.isTopScore){
                Image(
                    painter = painterResource(id = R.drawable.new_high_score),
                    contentDescription = "top score",
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(guidline)
                        bottom.linkTo(guidline)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }


        }

        CustomColoredButton(
            text = "Draw again",
            color = primary,
            borderColor = Color.White,
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Preview
@Composable
private fun SpeedDrawResultScreenPreview() {
    ScribbleDashTheme {
        SpeedDrawResultScreen(
            uiState = SpeedDrawResultUiState(
                ratingUi = RatingUi(
                    rating = Meh(),
                    accuracyPercent = 54,
                    title = UiText.DynamicText("Meh"),
                    text = UiText.StringResource(R.string.feedback_oops_4)
                ),
                isTopScore = true,
                successCount = 12,
                isHighestNumberOfSuccesses = true
            ),
            onClose = {}
        )

    }
}