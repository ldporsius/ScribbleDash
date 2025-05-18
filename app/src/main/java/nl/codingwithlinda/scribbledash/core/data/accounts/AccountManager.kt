package nl.codingwithlinda.scribbledash.core.data.accounts

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.Purchase
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount

class AccountManager {

    val userAccount1 = UserAccount(
        id = "1",
    )
    private val userAccounts = listOf(
        userAccount1
    )

    private val _activeUser = MutableStateFlow<UserAccount?>(null)

    fun setActiveUser(userAccount: UserAccount) {
        _activeUser.value = userAccount
    }
    private val _observableBalance = MutableStateFlow<Int>(0)
    val observableBalanceActiveUser = combine(_activeUser, _observableBalance) { activeUser, balance ->
        activeUser?.balance() ?: balance
    }

    val transactions = _activeUser.value?.let {
        it.transactions.toList()
    } ?: emptyList()

    fun userOwnsProduct(userAccountId: String, productId: String): Boolean {
        val userAccount = userAccounts.find { it.id == userAccountId } ?: return false
        return userAccount.transactions.any { it.productId == productId }
    }
    fun processPurchase(userAccountId: String, productId: String, price: Int) {
        val userAccount = userAccounts.find { it.id == userAccountId } ?: return
        val purchase = Purchase(
            date = System.currentTimeMillis(),
            productId = productId,
            price = price
        )
        userAccount.transactions.add(purchase)
        _observableBalance.update {
            userAccount.balance()
        }
    }

    fun balance(userAccountId: String): Int {
        val userAccount = userAccounts.find { it.id == userAccountId } ?: return 0
        return userAccount.balance()

    }
}