package nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses

import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasImageProduct
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class CanvasWoodTexture: CanvasImageProduct() {
    override val imageResourceId: Int
        get() = R.drawable.wood_texture
    override val id: String
        get() = this::class.java.simpleName
    override val title: UiText
        get() = UiText.DynamicText("Wood Texture")
    override val price: Int
        get() = 0
}