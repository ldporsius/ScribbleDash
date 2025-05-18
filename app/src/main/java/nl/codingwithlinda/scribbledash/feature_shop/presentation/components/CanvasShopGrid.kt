package nl.codingwithlinda.scribbledash.feature_shop.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.data.shop.tiers.tierToColor
import nl.codingwithlinda.scribbledash.core.data.shop.tiers.tierToContainerColor
import nl.codingwithlinda.scribbledash.core.domain.model.shop.prices.PriceCalculator
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.CanvasInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.feature_shop.presentation.model.toUi


@Composable
fun List<CanvasInTier>.toShopContent(
    calculatePrice: (product: CanvasInTier) -> Int,
    isLocked: (product: ShopProduct) -> Boolean,
    isSelected: (product: ShopProduct) -> Boolean,
    onItemClick: (productId: String, price: Int) -> Unit
) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(this@toShopContent.size){
                index ->
            val item = this@toShopContent[index]
            val price = calculatePrice(item)
            ShopItem(
                title = item.tier.name,
                isLocked = isLocked(item.product),
                isSelected = isSelected(item.product),
                price = price,
                bgColor = tierToContainerColor(item.tier),
                fgColor = tierToColor(item.tier),
                content = {
                    item.product.toUi(
                        modifier = Modifier
                    )
                },
                onItemClick = {
                    onItemClick(item.product.id, price)
                }
            )
        }
    }
}
