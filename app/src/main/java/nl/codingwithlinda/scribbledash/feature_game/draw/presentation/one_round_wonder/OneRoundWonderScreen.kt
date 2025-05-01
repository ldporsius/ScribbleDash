package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.GameDrawBottomBar
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components.UserDrawCanvas
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.ui.theme.backgroundGradient

@Composable
fun OneRoundWonderScreen(
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
        OneRoundWonderTopBar(
            actionOnClose = actionOnClose,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
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

            UserDrawCanvas(
                gridColor = gridColor,
                offsetParser = offsetParser,
                pathDrawer = pathDrawer,
                uiState = uiState,
                onAction = onAction
            )
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