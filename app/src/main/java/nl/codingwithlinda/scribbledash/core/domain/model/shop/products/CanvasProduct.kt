package nl.codingwithlinda.scribbledash.core.domain.model.shop.products

import nl.codingwithlinda.scribbledash.core.domain.model.shop.prices.PriceCalculator

abstract class CanvasProduct: ShopProduct{

}


abstract class CanvasImageProduct: CanvasProduct(){
    abstract val imageResourceId: Int
}

abstract class CanvasColorProduct: CanvasProduct(){
    abstract val color: Int
}