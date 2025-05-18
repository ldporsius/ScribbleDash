package nl.codingwithlinda.scribbledash.core.data.shop.sales

import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.data.shop.product_manager.CanvasManager
import nl.codingwithlinda.scribbledash.core.data.shop.sales.prices.DefaultPriceCalculator
import nl.codingwithlinda.scribbledash.core.data.shop.sales.prices.PriceForTierPenCalculator
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.SalesManager
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.CanvasInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.Tier

class CanvassesSalesManager(
    accountManager: AccountManager,
): SalesManager<CanvasInTier>(accountManager
) {

    private val canvassesPerTier = mapOf(
        Tier.FREE to CanvasManager.canvassesFreeTier.map {
            CanvasInTier(
                tier = Tier.FREE,
                product = it
            )
        },
        Tier.BASIC to CanvasManager.canvassesBasicTier.map {
            CanvasInTier(
                tier = Tier.BASIC,
                product = it
            )
        },
        Tier.PREMIUM to CanvasManager.canvassesPremiumTier.map {
            CanvasInTier(
                tier = Tier.PREMIUM,
                product = it
            )
        },
        Tier.LEGENDARY to CanvasManager.canvassesLegendaryTier.map {
            CanvasInTier(
                tier = Tier.LEGENDARY,
                product = it
            )
        }
    )

    override fun getProductsPerTier(): Map<Tier, List<CanvasInTier>> {
       return canvassesPerTier
    }

    override fun freeProducts(): List<CanvasInTier> {
        return canvassesPerTier[Tier.FREE] ?: emptyList()
    }

}
