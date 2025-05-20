package nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasColorProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.MutedCoral
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.SoftKhaki
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class CanvasColorMutedCoral: CanvasColorProduct() {
    override val color: Int
        get() = MutedCoral.toArgb()
    override val id: String
        get() = this::class.java.simpleName
    override val title: UiText
        get() =  UiText.DynamicText("MutedCoral")
    override val price: Int
         = 0
}