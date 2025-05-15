package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.presentation.util.asString
import nl.codingwithlinda.scribbledash.core.presentation.design_system.buttons.CustomColoredButton
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameResultComparison
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.draw.EndlessTopBar
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.oneRoundWonderColor
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.speedDrawColor

@Composable
fun EndlessModeResultScreen(
    endlessResultUiState: EndlessResultUiState,
    onClose: () -> Unit,
    onFinish: () -> Unit,
    onNext: () -> Unit
) {


    endlessResultUiState.ratingUi ?: return
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EndlessTopBar(
            numberSuccess = endlessResultUiState.numberSuccess,
            actionOnClose = onClose,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Text(text = endlessResultUiState.ratingUi.accuracyPercent.toString() + "%",
            style = MaterialTheme.typography.displayLarge)

        GameResultComparison(
            examplePath = endlessResultUiState.examplePath,
            userPath = endlessResultUiState.userPath,
            checkIcon = { endlessResultUiState.CheckIcon() }
        )

        Spacer(modifier = Modifier.weight(.5f))
        Text(endlessResultUiState.ratingUi.title.asString(),
            style = MaterialTheme.typography.headlineLarge)
        Text(
            endlessResultUiState.ratingUi.text.asString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))
        CustomColoredButton(
            text = "Finish",
            onClick = {
                onFinish()
            },
            color = speedDrawColor,
            borderColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        )

        if (endlessResultUiState.isSuccessful) {

            CustomColoredButton(
                text = "Next drawing",
                onClick = {
                    onNext()
                },
                color = oneRoundWonderColor,
                borderColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            )
        }

    }

}