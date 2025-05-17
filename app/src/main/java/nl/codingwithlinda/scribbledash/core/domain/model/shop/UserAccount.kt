package nl.codingwithlinda.scribbledash.core.domain.model.shop

import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct

data class UserAccount(
    val id: String,

){
    var coins: Int = 0
    val transactions : MutableList<ShopProduct> = mutableListOf<ShopProduct>()
}
