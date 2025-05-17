package nl.codingwithlinda.scribbledash.core.data.shop.sales.prices

import nl.codingwithlinda.scribbledash.core.domain.model.shop.prices.PriceCalculator
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductInTier

class DefaultPriceCalculator: PriceCalculator{

    override fun calculatePrice(product: ProductInTier): Int {
        return product.product.price
    }
}