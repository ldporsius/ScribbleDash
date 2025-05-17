package nl.codingwithlinda.scribbledash.core.data.shop.sales.prices

import nl.codingwithlinda.scribbledash.core.domain.model.shop.prices.PriceCalculator
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.Tier

class PriceForTierCanvasCalculator(
): PriceCalculator{
    private val tiers = Tier.values()
    private val price = tiers.associateWith {
        when(it){
            Tier.FREE -> 0
            Tier.BASIC -> 80
            Tier.PREMIUM -> 150
            Tier.LEGENDARY -> 250
        }
    }

    override fun calculatePrice(product: ProductInTier): Int {
        val tier = product.tier
        return price[tier] ?: 0
    }

}