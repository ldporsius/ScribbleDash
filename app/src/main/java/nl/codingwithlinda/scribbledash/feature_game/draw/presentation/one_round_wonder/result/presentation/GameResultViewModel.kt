package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation

import android.graphics.Path
import androidx.core.graphics.plus
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.data.shop.product_manager.CanvasManager
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasProduct
import nl.codingwithlinda.scribbledash.core.domain.model.tools.MyShoppingCart
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.state.OneRoundResultUiState

class GameResultViewModel(
    private val shoppingCart: MyShoppingCart,
    private val ratingTextGenerator: RatingTextGenerator,
    private val gameEngine: GameEngineTemplate
): ViewModel() {

    private val ratingMapper = RatingMapper(ratingTextGenerator)

    private val _uiState = MutableStateFlow(OneRoundResultUiState(
        ratingUi = getRatingUi()
    ))
    val uiState = _uiState
        .onStart {
           _uiState.update {
               it.copy(
                   examplePath = examplePath(),
                   userPath = userPath(),
                   userBackgroundCanvas = userBackground(),
                   ratingUi = getRatingUi()
               )
           }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), _uiState.value)

    private fun userPath() = gameEngine.coloredPaths.value.also {
        println("GameResultViewModel.userPath() has size: ${it.size}")
    }
    private fun examplePath() = gameEngine.getResult().examplePath.reduceOrNull { acc, path ->
        acc + path
    } ?: Path()

   private suspend fun userBackground(): CanvasProduct?  {
       val canvas = shoppingCart.getMyShoppingCart().canvasProductId?.let {
            CanvasManager.getCanvasById(it)
        } ?: CanvasManager.canvassesFreeTier.firstOrNull()

       return canvas
    }

    private fun getRatingUi(): RatingUi {
        val accuracy = gameEngine.getAccuracy()
        println("GameResultViewModel.getRatingUi() accuracy: ${accuracy}")

        return ratingMapper.toUi(accuracy)
    }
}