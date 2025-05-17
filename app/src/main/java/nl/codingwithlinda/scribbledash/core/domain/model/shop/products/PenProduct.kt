package nl.codingwithlinda.scribbledash.core.domain.model.shop.products

import nl.codingwithlinda.scribbledash.R

abstract class PenProduct: ShopProduct {
    abstract val color: Int
    open val imageResourceId: Int = R.drawable.scribble


}