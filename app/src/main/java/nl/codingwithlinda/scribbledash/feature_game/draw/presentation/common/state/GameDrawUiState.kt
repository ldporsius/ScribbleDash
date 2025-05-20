package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state


import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import nl.codingwithlinda.scribbledash.core.data.shop.product_manager.CanvasManager
import nl.codingwithlinda.scribbledash.core.data.util.bitmapFromCanvasProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasProduct
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath

@Immutable
data class GameDrawUiState(
    val drawPaths: List<ColoredPath> = emptyList(),
    val canRedo: Boolean = false,
    val canUndo: Boolean = false,
    val currentPath: List<ColoredPath> = emptyList(),
    val canvasProduct: CanvasProduct? = CanvasManager.canvassesFreeTier.firstOrNull()
){
    fun isUndoAvailable() = canUndo
    fun isRedoAvailable() = canRedo

    fun canvasBackground(context: Context, requiredSize: Int): Bitmap{
        if (canvasProduct == null) return Bitmap.createBitmap(requiredSize, requiredSize, Bitmap.Config.ARGB_8888)
        return context.bitmapFromCanvasProduct(canvasProduct, requiredSize)
    }
}
