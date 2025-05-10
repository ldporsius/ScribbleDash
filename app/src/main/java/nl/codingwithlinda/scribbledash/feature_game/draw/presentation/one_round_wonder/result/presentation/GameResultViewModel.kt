package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation

import android.graphics.Path
import androidx.core.graphics.plus
import androidx.lifecycle.ViewModel
import nl.codingwithlinda.scribbledash.core.presentation.model.RatingUi
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingMapper
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate

class GameResultViewModel(
    private val ratingTextGenerator: RatingTextGenerator,
    private val gameEngine: GameEngineTemplate
): ViewModel() {

    private val ratingMapper = RatingMapper(ratingTextGenerator)


    val examplePath = gameEngine.getResult().examplePath.reduceOrNull { acc, path ->
        acc + path
    } ?: Path()
    val userPath = gameEngine.getResult().userPath

    fun getRatingUi(): RatingUi {
        val accuracy = gameEngine.getAccuracy()
        println("GameResultViewModel.getRatingUi() accuracy: ${accuracy}")

        return ratingMapper.toUi(accuracy)
    }
}