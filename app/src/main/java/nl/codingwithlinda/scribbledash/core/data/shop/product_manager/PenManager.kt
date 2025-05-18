package nl.codingwithlinda.scribbledash.core.data.shop.product_manager

import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenCrimsonRed
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenEmeraldGreen
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenFlameOrange
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenMidnightBlack
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenOceanBlue
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenRainbow
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenRoseQuartz
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenSunshineYellow
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct

object PenManager {

    val pensFreeTier: List<PenProduct> = listOf(
        PenMidnightBlack(),

    )
    val pensBasicTier = listOf(
        PenCrimsonRed(),
        PenSunshineYellow(),
        PenOceanBlue(),
        PenEmeraldGreen(),
        PenFlameOrange()
    )

    val pensPremiumTier = listOf(
        PenRoseQuartz()
    )

    val pensLegendaryTier: List<PenProduct> = listOf(
        PenRainbow()
    )
}