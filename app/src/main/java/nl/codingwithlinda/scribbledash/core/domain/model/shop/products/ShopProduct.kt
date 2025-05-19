package nl.codingwithlinda.scribbledash.core.domain.model.shop.products

import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

interface ShopProduct {
    val id: String
    val title: UiText
    val price: Int
    val type: ProductType
}