package nl.codingwithlinda.scribbledash.feature_game.level.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.backgroundGradient

@Composable
fun GameLevelScreen(
    actionOnClose: () -> Unit,
    actionOnLevel: (GameLevel) -> Unit,
    modifier: Modifier = Modifier) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = backgroundGradient
        )
    ){
        IconButton(
            onClick = { actionOnClose() },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription ="close"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Start drawing!",
                style = MaterialTheme.typography.displayMedium)
            Text(text = "Choose a difficulty setting",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(48.dp))
             Row(
                    modifier = Modifier
                        .height(125.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(modifier = Modifier
                        .align(Alignment.Bottom)
                        .clickable {
                            actionOnLevel(GameLevel.BEGINNER)
                        }
                    ) {
                        GameLevelComponent(
                            gameLevel = GameLevel.BEGINNER,
                            )
                    }
                    Box(modifier = Modifier
                        .align(Alignment.Top)
                        .clickable {
                            actionOnLevel(GameLevel.CHALLENGING)
                        }
                    ) {
                        GameLevelComponent(
                            gameLevel = GameLevel.CHALLENGING,
                            )
                    }
                    Box(modifier = Modifier
                        .align(Alignment.Bottom)
                        .clickable {
                            actionOnLevel(GameLevel.MASTER)
                        }
                    ) {
                        GameLevelComponent(
                            gameLevel = GameLevel.MASTER,
                            )
                    }

            }
        }
    }
}