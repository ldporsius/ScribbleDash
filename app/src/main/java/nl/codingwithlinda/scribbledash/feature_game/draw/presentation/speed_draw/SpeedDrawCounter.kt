package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.speed_draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.ui.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.ui.theme.headlineXSmall
import nl.codingwithlinda.scribbledash.ui.theme.surfaceHigh

@Composable
fun SpeedDrawCounter(
    timeLeft: String,
    modifier: Modifier = Modifier
) {

    Box(modifier = Modifier
        .width(56.dp)
        .height(56.dp)
        .shadow(elevation = 4.dp, shape = CircleShape)
        .background(surfaceHigh, shape = CircleShape)
        ,
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(timeLeft,
            style = headlineXSmall,
        )
    }
}

@Preview
@Composable
private fun SpeedDrawCounterPreview() {
    ScribbleDashTheme {
        SpeedDrawCounter(timeLeft = "2:00")

    }
}