package nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers

import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductInTier

data class CanvasInTier(
    override val tier: Tier,
    override val product: CanvasProduct
    ): ProductInTier

