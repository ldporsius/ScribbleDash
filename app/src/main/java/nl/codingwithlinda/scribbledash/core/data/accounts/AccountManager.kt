package nl.codingwithlinda.scribbledash.core.data.accounts

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.di.DATASTORE_BALANCE_KEY
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.Purchase
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount

class AccountManager(
    private val dataStore: DataStore<Preferences>
) {

    val userAccount1 = UserAccount(
        id = "1",
    )
    private val userAccounts = listOf(
        userAccount1
    )

    private val _activeUser = MutableStateFlow<UserAccount?>(null)

    suspend fun setActiveUser(userAccount: UserAccount) {
        println("SETTING ACTIVE USER: ${userAccount.id}")

        dataStore.data.firstOrNull()?.let {
            println("DATASTORE BALANCE: ${it[DATASTORE_BALANCE_KEY]}")
            userAccount.addCoins(it[DATASTORE_BALANCE_KEY] ?: 0)
        }
        _activeUser.value = userAccount

        println("ACTIVE USER BALANCE: ${userAccount.balance()}")
    }

    val observableBalanceActiveUser = combine(_activeUser, dataStore.data) { activeUser, datastore ->
        datastore[DATASTORE_BALANCE_KEY] ?: 0
    }

    fun userOwnsProduct(userAccountId: String, productId: String): Boolean {
        val userAccount = userAccounts.find { it.id == userAccountId } ?: return false
        return userAccount.transactions.any { it.productId == productId }
    }

    suspend fun processPurchase(userAccountId: String, productId: String, price: Int) {
        val userAccount = userAccounts.find { it.id == userAccountId } ?: return
        val purchase = Purchase(
            date = System.currentTimeMillis(),
            productId = productId,
            price = price
        )
        userAccount.transactions.add(purchase)

        updateBalanceInDataStore(userAccountId)
    }

    suspend fun processReward(coins: Int) {
        _activeUser.value?.run {
            addCoins(coins)
            updateBalanceInDataStore(id)
        }
    }

    fun balance(userAccountId: String): Int {
        val userAccount = userAccounts.find { it.id == userAccountId } ?: return 0
        return userAccount.balance()
    }

    private suspend fun updateBalanceInDataStore(userAccountId: String){
        println("UPDATING BALANCE IN DATASTORE, BALANCE: ${balance(userAccountId)}")
        dataStore.edit {
            it[DATASTORE_BALANCE_KEY] = balance(userAccountId)
        }
    }
}