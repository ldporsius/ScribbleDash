package nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasColorProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.SoftKhaki
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class CanvasColorSoftKhaki: CanvasColorProduct() {
    override val color: Int
        get() = SoftKhaki.toArgb()
    override val id: String
        get() = this::class.java.simpleName
    override val title: UiText
        get() =  UiText.DynamicText("SoftKhaki")
    override val price: Int
         = 0
}