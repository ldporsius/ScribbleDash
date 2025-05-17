package nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers

import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductInTier

data class PenInTier(
    override val tier: Tier,
    override val product: PenProduct
    ): ProductInTier
