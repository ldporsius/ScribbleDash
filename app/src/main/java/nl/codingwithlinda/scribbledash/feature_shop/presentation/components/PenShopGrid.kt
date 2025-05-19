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
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.JsonNull.content
import nl.codingwithlinda.scribbledash.core.data.shop.tiers.tierToColor
import nl.codingwithlinda.scribbledash.core.data.shop.tiers.tierToContainerColor
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.White
import nl.codingwithlinda.scribbledash.feature_shop.presentation.model.toUi

@Composable
fun List<PenInTier>.toPenShopContent(
    calculatePrice: (product: PenInTier) -> Int,
    isLocked: (product: ShopProduct) -> Boolean,
    isSelected: (product: ShopProduct) -> Boolean,
    onItemClick: (product: ShopProduct, price: Int) -> Unit,
) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(this@toPenShopContent.size){
            index ->
            val item = this@toPenShopContent[index]
            val price = calculatePrice(item)
            ShopItem(
                title = item.tier.name,
                isLocked = isLocked(item.product),
                isSelected = {isSelected(item.product)},
                price = price,
                bgColor = tierToContainerColor(item.tier),
                fgColor = tierToColor(item.tier),
                content = {
                    item.product.toUi(
                        modifier = Modifier.background(
                            color = White,
                            shape = RoundedCornerShape(25)
                        )
                    )
                },
                onItemClick = {
                    onItemClick(item.product, price )
                }
            )
        }
    }
}