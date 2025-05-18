package nl.codingwithlinda.scribbledash.feature_shop.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.data.shop.tiers.tierToColor
import nl.codingwithlinda.scribbledash.core.data.shop.tiers.tierToContainerColor
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.White

@Composable
fun List<PenInTier>.toPenShopContent(
    calculatePrice: (product: PenInTier) -> Int,
    isLocked: (product: ShopProduct) -> Boolean,
    onItemClick: (productId: String, price: Int) -> Unit,
) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(this@toPenShopContent.size){
            index ->
            val item = this@toPenShopContent[index]
            val price = calculatePrice(item)
            ShopItem(
                title = item.tier.name,
                isLocked = isLocked(item.product),
                price = price,
                bgColor = tierToContainerColor(item.tier),
                fgColor = tierToColor(item.tier),
                content = {
                    PenShopContent(
                        centralImage = item.product.imageResourceId,
                        centralImageColor = Color(item.product.color),
                        modifier = Modifier.background(
                            color = White,
                            shape = RoundedCornerShape(25)
                        )

                    )
                },
                onItemClick = {
                    onItemClick(item.product.id, price )
                }
            )
        }
    }
}