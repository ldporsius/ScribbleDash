package nl.codingwithlinda.scribbledash.feature_shop.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import nl.codingwithlinda.scribbledash.R

@Composable
fun PenShopContent(
    centralImage: Int = R.drawable.scribble,
    centralImageColor: Color = Color.Black,
    modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = centralImage),
        contentDescription = "scribble",
        colorFilter = ColorFilter.tint(centralImageColor)
    )
}