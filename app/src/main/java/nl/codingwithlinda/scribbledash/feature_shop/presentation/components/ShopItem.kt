package nl.codingwithlinda.scribbledash.feature_shop.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonNull.content
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.components.CounterComponent
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.BasicTierColor
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.success
import nl.codingwithlinda.scribbledash.core.presentation.util.applyIf

@Composable
fun ShopItem(
    title: String = "BASIC",
    price: Int,
    isLocked: Boolean = true,
    isSelected: ()->Boolean,
    content: @Composable () -> Unit,
    fgColor: Color = MaterialTheme.colorScheme.onSurface,
    bgColor: Color = BasicTierColor,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier) {

    val selectedColor = success

    Box(
        modifier = modifier
            .requiredWidth(122.dp)
            .height(160.dp)
    ){
        Box(modifier = Modifier
            .width(110.dp)
            .height(152.dp)
            .align(Alignment.BottomStart)
            .applyIf(isSelected()){
                border(width = 2.dp,
                    color = selectedColor,
                    shape = RoundedCornerShape(10))
            }
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10))
            .background(color = bgColor, shape = RoundedCornerShape(10))
            .clickable {
                onItemClick()
            }
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title,
                    style = MaterialTheme.typography.labelSmall,
                    color = fgColor
                )
                Box(
                    modifier = Modifier

                        .border(width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = RoundedCornerShape(25))
                        .aspectRatio(1.5f)
                        .weight(1f)
                        .background(color = Color.Transparent,
                            shape = RoundedCornerShape(25)),
                    contentAlignment = Alignment.Center

                ) {
                    content()
                    if (isLocked){
                        Image(
                            painter = painterResource(R.drawable.lock),
                            contentDescription = "lock"
                        )
                    }

                }

                if (isLocked){
                   CounterComponent(
                       text = "$price",
                       backgroundColor = Color.Transparent,
                       foregroundColor = fgColor,
                       imageResourceId = R.drawable.coin,
                       imageSize = 16.dp,
                       modifier = Modifier
                           .height(20.dp)
                           .width(66.dp)

                   )
                }
                if (!isLocked){
                    Image(
                        painter = painterResource(R.drawable.cart),
                        contentDescription = "lock",
                        modifier = Modifier
                    )
                }


            }
        }

        if (isSelected()){
            ProductSelectedIcon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)

            )
        }
    }
}

@Preview
@Composable
private fun ShopItemPreview() {
    ScribbleDashTheme {
        ShopItem(
            price = 10,
            content = {},
            isSelected = {true},
            onItemClick = {}
        )
    }
}