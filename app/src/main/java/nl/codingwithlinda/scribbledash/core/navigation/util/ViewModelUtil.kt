package nl.codingwithlinda.scribbledash.core.navigation.util

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.scribbledash.core.domain.model.tools.MyShoppingCart
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel

object ViewModelUtil {

    @Composable
    fun createGameDrawViewModel(
        gameEngine: GameEngineTemplate,
        shoppingCart: MyShoppingCart
    ): GameDrawViewModel{
        val careTaker = PathDataCareTaker()

        val factory = viewModelFactory {
            initializer {
                GameDrawViewModel(
                    careTaker = careTaker,
                    gameEngine = gameEngine,
                    shoppingCart = shoppingCart
                )
            }
        }
        val viewModel = viewModel<GameDrawViewModel>(
            factory = factory
        )

        return viewModel
    }
}