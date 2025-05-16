package nl.codingwithlinda.scribbledash.feature_home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.presentation.design_system.components.CounterComponent
import nl.codingwithlinda.scribbledash.core.presentation.model.GameModeUi
import nl.codingwithlinda.scribbledash.core.presentation.util.asString

@Composable
fun HomeScreen(
    gameModes: List<GameModeUi>,
    actionOnGameMode: (GameMode) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Scribble Dash",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
            )
            CounterComponent(
                text = "0",
                imageResourceId = R.drawable.coin
            )
        }


        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Start drawing!",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(text = "Select game mode",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        gameModes.forEach { gameMode ->
            OutlinedCard(
                onClick = {
                    actionOnGameMode(gameMode.gameMode)
                },
                modifier = Modifier
                    .width(380.dp)
                    .height(128.dp)
                    .padding(bottom = 8.dp)
                ,
                border = BorderStroke(width = 8.dp, color = gameMode.color)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = gameMode.title.asString(),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 22.dp, end = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Image(
                        painter = painterResource(id = gameMode.imageResourceId),
                        contentDescription = gameMode.title.asString(),
                        modifier = Modifier
                            .fillMaxHeight()
                            //.aspectRatio(1f, true)

                        ,
                        contentScale = ContentScale.FillHeight
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
