package nl.codingwithlinda.scribbledash.core.data.shop.sales

import nl.codingwithlinda.scribbledash.core.data.shop.product_manager.PenManager
import nl.codingwithlinda.scribbledash.core.data.shop.sales.prices.DefaultPriceCalculator
import nl.codingwithlinda.scribbledash.core.data.shop.sales.prices.PriceForTierPenCalculator
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.SalesManager
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.Tier

class PensSalesManager: SalesManager<PenInTier>() {

    private val priceCalculator = DefaultPriceCalculator()
    private val priceForTierCalculator = PriceForTierPenCalculator()

    private val pensPerTier = mapOf(
        Tier.FREE to PenManager.pensFreeTier.map { product ->
            PenInTier(
                tier = Tier.FREE,
                product = product
            )
        },
        Tier.BASIC to PenManager.pensBasicTier.map {product ->
            PenInTier(
                tier = Tier.BASIC,
                product = product
            )
        }
        ,
        Tier.PREMIUM to PenManager.pensPremiumTier.map {product ->
            PenInTier(
                tier = Tier.PREMIUM,
                product = product
            )
        },
        Tier.LEGENDARY to PenManager.pensLegendaryTier.map { product ->
            PenInTier(
                tier = Tier.LEGENDARY,
                product = product
            )
        }
    )

    override fun getProductsPerTier(): Map<Tier, List<PenInTier>> {
       return pensPerTier
    }


}
