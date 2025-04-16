package nl.codingwithlinda.scribbledash.core.domain.result_manager

import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult

enum class ResultManager {
    INSTANCE;

    private val results = mutableListOf<DrawResult>()
    val resultsList: List<DrawResult> = results.toList()

    fun addResult(result: DrawResult) {
        results.add(result)
    }

    fun getLastResult(): DrawResult? {
        return results.lastOrNull()
    }

    fun updateResult(result: DrawResult) {
        results.remove(result)
        results.add(result)
    }
}