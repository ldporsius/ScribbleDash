package nl.codingwithlinda.scribbledash.core.data.shop.products.pens

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.CrimsonRed
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class PenCrimsonRed(): PenProduct() {
    override val color: Int
         = CrimsonRed.toArgb()
    override val id: String
        = "penBlack"
    override val title: UiText
        = UiText.DynamicText("CrimsonRed")
    override val price: Int
        = 20
}