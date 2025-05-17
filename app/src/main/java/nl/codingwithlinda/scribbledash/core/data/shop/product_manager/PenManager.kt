package nl.codingwithlinda.scribbledash.core.data.shop.product_manager

import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenCrimsonRed
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenMidnightBlack
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenSunshineYellow
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct

object PenManager {

    val pensFreeTier: List<PenProduct> = listOf(
        PenMidnightBlack(),

    )
    val pensBasicTier = listOf(
        PenCrimsonRed(),
        PenSunshineYellow(),
    )

    val pensPremiumTier = listOf(

        PenCrimsonRed(),
        PenSunshineYellow(),
    )

    val pensLegendaryTier = listOf(

        PenCrimsonRed(),
        PenSunshineYellow(),
    )
}