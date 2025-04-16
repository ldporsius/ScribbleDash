package nl.codingwithlinda.scribbledash.feature_game.result.presentation.state

sealed interface GameResultAction {
    data object TryAgain: GameResultAction
    data object Close: GameResultAction

}