package nl.codingwithlinda.scribbledash.core.domain.util

sealed interface ScResult<D,E: Error> {
    data class Success<D,E: Error>(val data: D): ScResult<D,E>
    data class Failure<D,E: Error>(val error: E): ScResult<D,E>
}