package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.ui.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.ui.theme.headlineXSmall
import nl.codingwithlinda.scribbledash.ui.theme.surfaceLow

@Composable
fun GameSuccessCounter(
    successes: String,
    ) {

    Box(modifier = Modifier
        .size(width = 76.dp, height = 36.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ){

        Box(modifier = Modifier
            .size(width = 60.dp, height = 28.dp)
            .background(color = surfaceLow, shape = RoundedCornerShape(50))
            .align(androidx.compose.ui.Alignment.CenterEnd),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ){
            Text(successes,
                style = headlineXSmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Image(
            painter = painterResource(R.drawable.game_level_3),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterStart)
        )

    }

}

@Preview
@Composable
private fun GameSuccessCounterPreview() {

    ScribbleDashTheme {
        GameSuccessCounter(successes = "2")

    }
}