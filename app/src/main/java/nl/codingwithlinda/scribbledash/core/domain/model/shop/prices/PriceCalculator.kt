package nl.codingwithlinda.scribbledash.core.domain.model.shop.prices

import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct

interface PriceCalculator{
    fun calculatePrice(product: ProductInTier): Int

}