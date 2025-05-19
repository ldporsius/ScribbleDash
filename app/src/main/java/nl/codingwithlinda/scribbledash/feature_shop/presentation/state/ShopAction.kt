package nl.codingwithlinda.scribbledash.feature_shop.presentation.state

import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct

sealed interface ShopAction {
    data class ItemClick(val product: ShopProduct, val price: Int): ShopAction
}