package nl.codingwithlinda.scribbledash.core.domain.model.shop.sales

import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct

data class ShoppingCart(
    val pen: PenProduct? = null,
    val canvas: CanvasProduct? = null
)
