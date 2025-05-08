package nl.codingwithlinda.scribbledash.core.domain.result_manager


enum class ResultManager {
    INSTANCE;

    //var level: GameLevel = GameLevel.BEGINNER

   /* private var result: AndroidDrawResult? = null

    private fun newResult(level: GameLevel) : AndroidDrawResult {
        return AndroidDrawResult(
            id = System.currentTimeMillis().toString(),
            level = level,
        )
    }

    fun addResult(level: GameLevel) {
        result = newResult(level)
    }

    fun getLastResult(): AndroidDrawResult? {
        result ?: addResult(GameLevel.BEGINNER)
        return result
    }


    fun updateResult(result: AndroidDrawResult) {
        this.result = result
    }*/
}