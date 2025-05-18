package nl.codingwithlinda.scribbledash.core.domain.model.accounts

import androidx.compose.ui.util.fastSumBy


data class UserAccount(
    val id: String,
){
    var coins: Int = 0
    val transactions : MutableList<Purchase> = mutableListOf<Purchase>()

    fun addCoins(amount: Int){
        coins += amount
    }
    fun removeCoins(amount: Int) {
        coins -= amount
    }

    fun balance(): Int {
        return coins - transactions.fastSumBy { it.price }
    }
}
