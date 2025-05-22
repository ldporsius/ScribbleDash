package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.state

import android.graphics.Path
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasProduct
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath

data class OneRoundResultUiState(
    val examplePath: Path = Path(),
    val userPath: List<ColoredPath> = emptyList(),
    val userBackgroundCanvas: CanvasProduct? = null,
    val ratingUi: RatingUi,
    val numberSuccess: Int = 0,
    val isSuccessful: Boolean = false
){

    @Composable
    fun CheckIcon() {
       if(isSuccessful){
            Image(painter = painterResource(R.drawable.check_success), contentDescription = null)
        } else{
            Image(painter = painterResource(R.drawable.check_failure), contentDescription = null)
        }
    }

}
