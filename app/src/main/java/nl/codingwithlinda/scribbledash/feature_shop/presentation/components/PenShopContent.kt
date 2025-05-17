package nl.codingwithlinda.scribbledash.feature_shop.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R

@Composable
fun PenShopContent(
    centralImage: Int = R.drawable.scribble,
    centralImageColor: Color = Color.Black,
    modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = centralImage),
            contentDescription = "scribble",
            colorFilter = ColorFilter.tint(centralImageColor),
            modifier = Modifier.fillMaxSize().padding(4.dp),
            contentScale = ContentScale.FillWidth

        )
    }
}