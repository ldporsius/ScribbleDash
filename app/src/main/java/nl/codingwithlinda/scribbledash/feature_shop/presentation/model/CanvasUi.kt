package nl.codingwithlinda.scribbledash.feature_shop.presentation.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasColorProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasImageProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasProduct

@Composable
fun CanvasProduct.toUi(
    modifier: Modifier = Modifier
){

    if (this is CanvasImageProduct){
        Image(painter = painterResource(id = imageResourceId),
            contentDescription = "canvas",
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Transparent, shape = RoundedCornerShape(25))
                .clip(RoundedCornerShape(25))
        )
    }
    if (this is CanvasColorProduct){
        val color = Color(this.color)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .background(color = color, shape = RoundedCornerShape(25))
        )

    }
}