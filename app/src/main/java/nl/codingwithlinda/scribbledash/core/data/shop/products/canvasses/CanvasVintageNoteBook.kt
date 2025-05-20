package nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses

import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasImageProduct
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class CanvasVintageNoteBook: CanvasImageProduct() {
    override val imageResourceId: Int
        get() = R.drawable.vintage_notebook
    override val id: String
        get() = this::class.java.simpleName
    override val title: UiText
        get() = UiText.DynamicText("Vintage notebook")
    override val price: Int
        get() = 0
}