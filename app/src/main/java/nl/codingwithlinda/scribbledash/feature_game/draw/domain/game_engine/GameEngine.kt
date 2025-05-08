package nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine

import kotlinx.coroutines.flow.Flow
import nl.codingwithlinda.scribbledash.core.data.draw_examples.PathCoordinates
import nl.codingwithlinda.scribbledash.core.data.util.error.GameError
import nl.codingwithlinda.scribbledash.core.domain.model.AndroidDrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.util.Error
import nl.codingwithlinda.scribbledash.core.domain.util.ScResult
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

interface GameEngine {
    fun setLevel(level: GameLevel)
    fun provideExample(): DrawResult
    val showExample: Flow<Boolean>
    val countDown: Flow<Int>
    fun processUserInput(userInput: List<PathData>)
    fun endUserInput(callback: (ScResult<Any, GameError>) -> Unit)
    fun getResult(): DrawResult
}