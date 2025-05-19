package nl.codingwithlinda.scribbledash.core.domain.model.shop.sales

data class ShoppingCart(
    val penProductId : String? = null,
    val canvasProductId: String? = null
){
    companion object{
        const val PEN_PRODUCT_ID = "penProductId"
        const val CANVAS_PRODUCT_ID = "canvasProductId"
    }
    override fun toString(): String{
        return "$penProductId;$canvasProductId"
    }
}
