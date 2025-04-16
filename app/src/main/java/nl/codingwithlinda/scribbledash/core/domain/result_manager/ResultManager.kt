package nl.codingwithlinda.scribbledash.core.domain.result_manager

import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult

enum class ResultManager {
    INSTANCE;

    val results = mutableListOf<DrawResult>()
}