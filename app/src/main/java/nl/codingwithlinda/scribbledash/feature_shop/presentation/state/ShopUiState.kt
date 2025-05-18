package nl.codingwithlinda.scribbledash.feature_shop.presentation.state

import nl.codingwithlinda.scribbledash.core.data.shop.sales.prices.PriceForTierCanvasCalculator
import nl.codingwithlinda.scribbledash.core.data.shop.sales.prices.PriceForTierPenCalculator
import nl.codingwithlinda.scribbledash.core.domain.model.shop.prices.PriceCalculator
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.CanvasInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier

data class ShopUiState(
    val pricePenCalculator: PriceCalculator = PriceForTierPenCalculator(),
    val priceCanvasCalculator: PriceCalculator = PriceForTierCanvasCalculator(),
    val penProducts: List<PenInTier> = emptyList(),
    val availablePenProducts: List<String> = emptyList(),
    val canvasProducts: List<CanvasInTier> = emptyList(),
    val availableCanvasProducts: List<String> = emptyList()

){
    fun isPenLocked(productId: String): Boolean{
        return productId !in availablePenProducts
    }

    fun isCanvasLocked(productId: String): Boolean{
        return productId !in availableCanvasProducts
    }
}
