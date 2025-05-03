package nl.codingwithlinda.scribbledash.core.data.util.error

import nl.codingwithlinda.scribbledash.core.domain.util.Error

sealed interface ParseError: Error {
    data object GameModeParseError: ParseError
}