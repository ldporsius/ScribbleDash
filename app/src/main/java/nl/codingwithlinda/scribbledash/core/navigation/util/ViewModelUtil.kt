package nl.codingwithlinda.scribbledash.core.navigation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser.AndroidOffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.GameDrawViewModel

object ViewModelUtil {
    val pathCreator =
        StraightPathCreator()
    val offsetParser = AndroidOffsetParser

    @Composable
    fun createGameDrawViewModel(gameEngine: GameEngineTemplate): GameDrawViewModel{
        val careTaker = PathDataCareTaker()

        val factory = viewModelFactory {
            initializer {
                GameDrawViewModel(
                    careTaker = careTaker,
                    offsetParser = offsetParser,
                    gameEngine = gameEngine,
                    pathDrawer = pathCreator
                )
            }
        }
        val viewModel = viewModel<GameDrawViewModel>(
            factory = factory
        )

        return viewModel
    }
}