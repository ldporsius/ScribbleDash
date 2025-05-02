package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.one_round_wonder.result.presentation.state

sealed interface GameResultAction {
    data object TryAgain: GameResultAction
    data object Close: GameResultAction

}