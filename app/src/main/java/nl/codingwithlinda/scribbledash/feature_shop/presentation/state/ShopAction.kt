package nl.codingwithlinda.scribbledash.feature_shop.presentation.state

sealed interface ShopAction {

    data class BuyProduct(val productId: String): ShopAction
}