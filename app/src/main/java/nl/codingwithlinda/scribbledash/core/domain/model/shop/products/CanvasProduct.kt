package nl.codingwithlinda.scribbledash.core.domain.model.shop.products


abstract class CanvasProduct: ShopProduct{
    override val type: ProductType = ProductType.CANVAS
}

abstract class CanvasImageProduct: CanvasProduct(){
    abstract val imageResourceId: Int
}

abstract class CanvasColorProduct: CanvasProduct(){
    abstract val color: Int
}