package nl.codingwithlinda.scribbledash.feature_shop.presentation.model

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.resourceToDrawPaths
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.BasicPenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.MultiColorPenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.RainbowPenBrush
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.rainbowColors
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.MultiColorPathCreator

@Composable
fun PenProduct.toUi(
    modifier: Modifier = Modifier
){
    if (this is BasicPenProduct){
        val color = Color(this.color)
        val centralImage = this.imageResourceId

        Box(modifier = modifier) {
            Image(
                painter = painterResource(id = centralImage),
                contentDescription = "scribble",
                colorFilter = ColorFilter.tint(color),
                modifier = Modifier.fillMaxSize().padding(4.dp),
                contentScale = ContentScale.FillWidth

            )
        }
    }

    if (this is MultiColorPenProduct){
        val centralImage = this.imageResourceId
        val context = LocalContext.current
        val resources = LocalContext.current.resources

        val colors = this.colors

        val paths = resourceToDrawPaths(centralImage, context).map {
            pathToCoordinates(it, 1f)
        }.flatten()
            .map {
                Offset(it.x, it.y)
            }

        val coloredPaths = MultiColorPathCreator(colors).drawPath(paths).paths


        coloredPaths.fastForEach{
            it.path.asComposePath().transform(
                Matrix(

                ).apply {
                   this.scale(3.5f, 4f)
                }
            )
        }
        Box(modifier = modifier) {
            androidx.compose.foundation.Canvas(
                modifier = Modifier.fillMaxSize()
                    .padding(4.dp)
            ) {
                coloredPaths.forEachIndexed { index, path ->
                    drawPath(
                        path = path.path.asComposePath(),
                        color = Color(path.color),
                        style = Stroke(10f)

                    )
                }
            }



        }
    }
}