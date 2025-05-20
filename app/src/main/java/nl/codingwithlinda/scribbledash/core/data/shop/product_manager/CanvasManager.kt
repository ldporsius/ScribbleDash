package nl.codingwithlinda.scribbledash.core.data.shop.product_manager

import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorDustyBlue
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorFadedOlive
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorLightGray
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorLightSageGreen
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorMutedCoral
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorMutedMauve
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorPaleBeige
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorPaleMint
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorPalePeach
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorSoftKhaki
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorSoftLavender
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorSoftLilac
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorSoftPowderBlue
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasColorWhite
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasVintageNoteBook
import nl.codingwithlinda.scribbledash.core.data.shop.products.canvasses.CanvasWoodTexture

object CanvasManager {

    val canvassesFreeTier = listOf(
        CanvasColorWhite(),
    )
    val canvassesBasicTier = listOf(
        CanvasColorLightGray(),
        CanvasColorPaleBeige(),
        CanvasColorSoftPowderBlue(),
        CanvasColorLightSageGreen(),
        CanvasColorPalePeach(),
        CanvasColorSoftLavender(),
    )
    val canvassesPremiumTier = listOf(
        CanvasColorFadedOlive(),
        CanvasColorMutedMauve(),
        CanvasColorDustyBlue(),
        CanvasColorSoftKhaki(),
        CanvasColorMutedCoral(),
        CanvasColorPaleMint(),
        CanvasColorSoftLilac(),
    )

    val canvassesLegendaryTier = listOf(
        CanvasWoodTexture(),
        CanvasVintageNoteBook()

    )
}