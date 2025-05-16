package nl.codingwithlinda.scribbledash.feature_shop.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.ScribbleDashTheme

@Composable
fun ShopItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ){
        Box(modifier = Modifier
            .padding(4.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(25))

            .width(110.dp)
            .height(152.dp)

            .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(25))
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text("BASIC",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .border(width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = RoundedCornerShape(25))
                        .padding(4.dp)
                        .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(25))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.scribble),
                        contentDescription = "scribble"
                    )
                }



            }
        }
    }
}

@Preview
@Composable
private fun ShopItemPreview() {
    ScribbleDashTheme {
        ShopItem()
    }
}