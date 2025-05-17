package nl.codingwithlinda.scribbledash.feature_shop.presentation.state

sealed interface ShopAction {

    data class ItemClickPen(val productId: String): ShopAction
    data class ItemClickCanvas(val productId: String): ShopAction
}