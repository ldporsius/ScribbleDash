package nl.codingwithlinda.scribbledash.core.data.util.error

import nl.codingwithlinda.scribbledash.core.domain.util.Error

sealed interface GameError: Error {
    data object BelowStandardAccuracy : GameError
    data object GameOverError : GameError
}