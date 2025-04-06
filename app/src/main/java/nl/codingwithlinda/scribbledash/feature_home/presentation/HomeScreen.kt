package nl.codingwithlinda.scribbledash.feature_home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode

@Composable
fun HomeScreen(
    actionOnGameMode: (GameMode) -> Unit
) {

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            actionOnGameMode(GameMode.ONE_ROUND_WONDER)
        }) {
            Text(text = "One round wonder")

        }
    }
}