package nl.codingwithlinda.scribbledash.core.data.accounts

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.di.DATASTORE_BALANCE_KEY
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.Purchase
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount

class AccountManager private constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object{
        @Volatile
        private var instance: AccountManager? = null

        @Synchronized
       fun Instance(dataStore: DataStore<Preferences>): AccountManager {
           if (instance == null){
               instance = AccountManager(dataStore)
           }
            return instance!!
       }
    }

    val userAccount1 = UserAccount(
        id = "1",
    )


    suspend fun setActiveUser(userAccount: UserAccount) {
        println("SETTING ACTIVE USER: ${userAccount.id}")

        dataStore.data.firstOrNull()?.let {
            println("DATASTORE BALANCE: ${it[DATASTORE_BALANCE_KEY]}")
            userAccount.removeCoins(userAccount.coins)
            userAccount.addCoins(it[DATASTORE_BALANCE_KEY] ?: 0)
        }

        println("ACTIVE USER BALANCE: ${userAccount.balance()}")
    }

    val observableBalanceActiveUser = dataStore.data
        .catch {
            println("DATASTORE ERROR: ${it.message}")
        }.map {
            it[DATASTORE_BALANCE_KEY] ?: 0
        }


    fun userOwnsProduct(userAccountId: String, productId: String): Boolean {
        return userAccount1.transactions.any { it.productId == productId }
    }

    suspend fun processPurchase(userAccountId: String, productId: String, price: Int) {

        val purchase = Purchase(
            date = System.currentTimeMillis(),
            productId = productId,
            price = price
        )
        userAccount1.transactions.add(purchase)

        updateBalanceInDataStore(userAccountId)

        println("PROCESSed PURCHASE, user owns: ${userAccount1.transactions}")
    }

    suspend fun processReward(coins: Int) {
        userAccount1.run {
            addCoins(coins)
            updateBalanceInDataStore(id)
        }
    }

    fun balance(userAccountId: String): Int {
        return userAccount1.balance()
    }

    private suspend fun updateBalanceInDataStore(userAccountId: String){
        println("UPDATING BALANCE IN DATASTORE, BALANCE: ${balance(userAccountId)}")
        dataStore.edit {
            it[DATASTORE_BALANCE_KEY] = balance(userAccountId)
        }
    }

    suspend fun donateCoins(amount: Int){
        userAccount1.addCoins(amount)
        dataStore.edit {
            it[DATASTORE_BALANCE_KEY] = it[DATASTORE_BALANCE_KEY]?.plus(amount) ?: amount
        }
    }

    suspend fun clearCoinsInDatastore(){
        dataStore.edit {
            it[DATASTORE_BALANCE_KEY] = 0
        }
    }
}