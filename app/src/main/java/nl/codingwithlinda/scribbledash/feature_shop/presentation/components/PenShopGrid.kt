package nl.codingwithlinda.scribbledash.feature_shop.presentation.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.core.data.shop.tiers.tierToColor
import nl.codingwithlinda.scribbledash.core.data.shop.tiers.tierToContainerColor
import nl.codingwithlinda.scribbledash.core.domain.model.shop.prices.PriceCalculator
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.CanvasInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.feature_shop.presentation.model.toUi

@Composable
fun List<PenInTier>.toPenShopContent(
    priceCalculator: PriceCalculator,
    isLocked: (product: ShopProduct) -> Boolean,
) {

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(this@toPenShopContent.size){
            index ->
            val item = this@toPenShopContent[index]
            ShopItem(
                title = item.tier.name,
                isLocked = isLocked(item.product),
                price = priceCalculator.calculatePrice(item),
                bgColor = tierToContainerColor(item.tier),
                fgColor = tierToColor(item.tier),
                content = {
                    PenShopContent(
                        centralImage = item.product.imageResourceId,
                        centralImageColor = Color(item.product.color)
                    )
                }
            )
        }
    }
}