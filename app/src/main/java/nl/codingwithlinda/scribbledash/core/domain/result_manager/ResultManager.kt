package nl.codingwithlinda.scribbledash.core.domain.result_manager

import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath

/*
// Usage:

 */
enum class ResultManager {
    INSTANCE;

    private var result: DrawResult? = null

    private fun newResult(level: GameLevel) : DrawResult {
        return DrawResult(
            id = System.currentTimeMillis().toString(),
            level = level,
        )
    }

    fun addResult(level: GameLevel) {
        result = newResult(level)
    }

    fun getLastResult(): DrawResult? {
        result ?: addResult(GameLevel.BEGINNER)
        return result
    }

    fun updateResult(examplePath: List<AndroidDrawPath>){
        result = result?.copy(
            examplePath = examplePath
        )
    }

    fun updateResult(result: DrawResult) {
        this.result = result
    }
}