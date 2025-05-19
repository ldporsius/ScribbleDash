package nl.codingwithlinda.scribbledash.core.data.shop.product_manager

import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenCopperAura
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenCoralReef
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenCrimsonRed
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenEmeraldGreen
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenFlameOrange
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenGoldenGlow
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenMajesticIndigo
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenMidnightBlack
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenOceanBlue
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenRainbow
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenRoseQuartz
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenRoyalPurple
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenSunshineYellow
import nl.codingwithlinda.scribbledash.core.data.shop.products.pens.PenTealDream
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
        PenRoseQuartz(),
        PenRoyalPurple(),
        PenTealDream(),
        PenGoldenGlow(),
        PenCoralReef(),
        PenMajesticIndigo(),
        PenCopperAura()
    )

    val pensLegendaryTier: List<PenProduct> = listOf(
        PenRainbow()
    )

    private val allPens: List<PenProduct> = pensFreeTier + pensBasicTier + pensPremiumTier + pensLegendaryTier

    fun getPenById(id: String): PenProduct {
        return allPens.find { it.id == id } ?: PenMidnightBlack()
    }
}