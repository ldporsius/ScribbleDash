package nl.codingwithlinda.scribbledash.feature_game.show_example.domain.usecase

import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager

class SaveRandomExampleUseCase(
    private val exampleProvider: AndroidDrawExampleProvider,
) {

    fun example(){
        val index = (0 until exampleProvider.examples.size).random()
        val example = exampleProvider.examples[index]

        ResultManager.INSTANCE.let { manager ->

            manager.getLastResult()?.let {lastResult ->
                manager.updateResult(
                    lastResult.copy(
                        examplePath = listOf( example)
                    )
                )
            }
        }
    }
}