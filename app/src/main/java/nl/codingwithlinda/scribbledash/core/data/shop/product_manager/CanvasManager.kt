package nl.codingwithlinda.scribbledash.core.data.shop.product_manager

import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorDustyBlue
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorFadedOlive
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorLightGray
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorLightSageGreen
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorMutedMauve
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorPaleBeige
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorPalePeach
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorSoftLavender
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorSoftPowderBlue
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorWhite

object CanvasManager {

    val canvassesFreeTier = listOf(
        CanvasColorWhite(),
    )
    val canvassesBasicTier = listOf(
        CanvasColorLightGray(),
        CanvasColorPaleBeige(),
        CanvasColorSoftPowderBlue(),
        CanvasColorLightSageGreen(),
    )
    val canvassesPremiumTier = listOf(
        CanvasColorPalePeach(),
        CanvasColorSoftLavender(),
        CanvasColorFadedOlive(),
        CanvasColorMutedMauve(),

    )

    val canvassesLegendaryTier = listOf(
        CanvasColorDustyBlue(),
    )
}