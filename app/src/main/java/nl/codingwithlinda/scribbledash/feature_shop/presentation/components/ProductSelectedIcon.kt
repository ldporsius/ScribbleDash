package nl.codingwithlinda.scribbledash.feature_shop.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.White

@Composable
fun ProductSelectedIcon(
    modifier: Modifier = Modifier) {
    Box(modifier = modifier

    ){
        Image(painter = painterResource(R.drawable.check_success_1),
            contentDescription = "selected",
            modifier = Modifier

        )

    }
}

@Preview
@Composable
private fun ProductSelectedIconPreview() {
    ScribbleDashTheme {
        ProductSelectedIcon()

    }
}