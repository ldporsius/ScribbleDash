package nl.codingwithlinda.scribbledash.core.data.util.error

import nl.codingwithlinda.scribbledash.core.domain.util.Error

sealed interface DatabaseError: Error{
    data class GenericError(val message: String): DatabaseError
}