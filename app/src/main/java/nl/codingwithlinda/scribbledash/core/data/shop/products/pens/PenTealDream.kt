package nl.codingwithlinda.scribbledash.core.data.shop.products.pens

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.BasicPenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.RoseQuartz
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.RoyalPurple
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.SunshineYellow
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.TealDream
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class PenTealDream(): BasicPenProduct() {
    override val color: Int
         = TealDream.toArgb()
    override val id: String
        = this::class.java.simpleName
    override val title: UiText
        = UiText.DynamicText("TealDream")
    override val price: Int
        = 0
}