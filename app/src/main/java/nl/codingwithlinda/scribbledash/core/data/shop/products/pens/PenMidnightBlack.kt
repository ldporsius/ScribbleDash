package nl.codingwithlinda.scribbledash.core.data.shop.products.pens

import androidx.compose.ui.graphics.toArgb
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.MidnightBlack
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

class PenMidnightBlack(): PenProduct() {
    override val color: Int
         = MidnightBlack.toArgb()
    override val id: String
        = "penBlack"
    override val title: UiText
        = UiText.DynamicText("MidnightBlack")
    override val price: Int
        = 0


}