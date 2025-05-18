package nl.codingwithlinda.scribbledash.core.domain.model.accounts

data class Purchase(
    val date: Long,
    val productId: String,
    val price: Int
)
