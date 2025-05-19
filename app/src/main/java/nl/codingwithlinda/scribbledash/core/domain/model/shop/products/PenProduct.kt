package nl.codingwithlinda.scribbledash.core.domain.model.shop.products

import nl.codingwithlinda.scribbledash.R

abstract class PenProduct: ShopProduct {
    open val imageResourceId: Int = R.drawable.scribble
    override val type: ProductType = ProductType.PEN
}
abstract class BasicPenProduct: PenProduct(){
    abstract val color: Int
}
abstract class MultiColorPenProduct: PenProduct(){
    abstract val colors: List<Int>
}