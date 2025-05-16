package nl.codingwithlinda.scribbledash.core.presentation.design_system.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.headlineXSmall
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onBackground
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.surfaceLow

@Composable
fun CounterComponent(
    text: String,
    imageResourceId: Int,
    backgroundColor: Color = surfaceLow,
    foregroundColor: Color = onBackground,
    modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .size(width = 76.dp, height = 36.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ){

        Box(modifier = Modifier
            .size(width = 60.dp, height = 28.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(50))
            .align(androidx.compose.ui.Alignment.CenterEnd),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ){
            Text(text,
                style = headlineXSmall,
                modifier = Modifier.padding(start = 8.dp),
                color = foregroundColor
            )
        }
        Image(
            painter = painterResource(imageResourceId),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterStart)
        )

    }
}