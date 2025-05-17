package nl.codingwithlinda.scribbledash.core.domain.model.shop.products

import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.Tier

interface ProductInTier{
    val tier: Tier
    val product: ShopProduct
}