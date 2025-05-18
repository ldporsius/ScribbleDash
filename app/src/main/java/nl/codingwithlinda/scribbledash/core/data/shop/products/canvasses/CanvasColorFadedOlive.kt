package nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasColorProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.FadedOlive
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.White
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class CanvasColorFadedOlive: CanvasColorProduct() {
    override val color: Int
        get() = FadedOlive.toArgb()
    override val id: String
        get() = this::class.java.simpleName
    override val title: UiText
        get() =  UiText.DynamicText("White")
    override val price: Int
         = 9
}