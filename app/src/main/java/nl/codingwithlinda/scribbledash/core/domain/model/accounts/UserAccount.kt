package nl.codingwithlinda.scribbledash.core.domain.model.accounts

import androidx.compose.ui.util.fastSumBy

data class UserAccount(
    val id: String,
){
    val lock = Any()
    var coins: Int = 0
    val transactions : MutableList<Purchase> = mutableListOf<Purchase>()

    @Synchronized
    fun addCoins(amount: Int){
        synchronized(lock){
            coins += amount
        }

    }
    fun removeCoins(amount: Int) {
        synchronized(lock){
            coins -= amount
        }
    }

    fun balance(): Int {
        return coins - transactions.fastSumBy { it.price }
    }
}
