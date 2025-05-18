package nl.codingwithlinda.scribbledash.core.presentation.design_system.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R

@Composable
fun CoinsEarnedComponent(
    coins: Int,
    modifier: Modifier = Modifier) {
    CounterComponent(
        text = "$coins",
        imageResourceId = R.drawable.coin,
        imageSize = 24.dp,
        modifier = modifier
    )
}