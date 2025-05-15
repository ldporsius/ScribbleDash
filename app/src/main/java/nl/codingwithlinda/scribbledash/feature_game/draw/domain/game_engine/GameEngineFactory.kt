package nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine

import nl.codingwithlinda.scribbledash.core.domain.model.GameMode

interface GameEngineFactory {

    fun createEngine(gameMode: GameMode): GameEngineTemplate
}