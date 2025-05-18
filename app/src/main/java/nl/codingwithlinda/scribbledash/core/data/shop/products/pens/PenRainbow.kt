package nl.codingwithlinda.scribbledash.core.data.shop.products.pens

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.MultiColorPenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.RainbowPenBrush
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.SunshineYellow
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.rainbowColors
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class PenRainbow(): MultiColorPenProduct() {
    override val colors: List<Int>
         = rainbowColors.map { it.toArgb() }
    override val id: String
        = this::class.java.simpleName
    override val title: UiText
        = UiText.DynamicText("SunshineYellow")
    override val price: Int
        = 0
}