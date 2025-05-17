package nl.codingwithlinda.scribbledash.core.data.shop.products.pens

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.SunshineYellow
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class PenSunshineYellow(): PenProduct() {
    override val color: Int
         = SunshineYellow.toArgb()
    override val id: String
        = "penSunshineYellow"
    override val title: UiText
        = UiText.DynamicText("SunshineYellow")
    override val price: Int
        = 0
}