package nl.codingwithlinda.scribbledash.core.data.shop.product_manager

import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenCrimsonRed
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenMidnightBlack
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenSunshineYellow

object PenManager {

    val pensFreeTier = listOf(
        PenMidnightBlack(),

    )
    val pensBasicTier = listOf(
        PenCrimsonRed(),
        PenSunshineYellow(),
    )

    val pensPremiumTier = listOf(
        PenMidnightBlack(),
        PenCrimsonRed(),
        PenSunshineYellow(),
    )

    val pensLegendaryTier = listOf(
        PenMidnightBlack(),
        PenCrimsonRed(),
        PenSunshineYellow(),
    )
}