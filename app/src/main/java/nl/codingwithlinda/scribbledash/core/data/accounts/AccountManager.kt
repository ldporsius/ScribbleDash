package nl.codingwithlinda.scribbledash.core.data.accounts

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import nl.codingwithlinda.scribbledash.core.di.DATASTORE_BALANCE_KEY
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.Purchase
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount

class AccountManager private constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val mutex = Mutex()
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

        val userLoggedInKey = stringPreferencesKey("UserLoggedIn")
        val OPEN_ACCOUNT_BONUS = 300
    }

    val userAccount1 = UserAccount(
        id = "1",
    )


    suspend fun setActiveUser(userAccount: UserAccount) {
        mutex.withLock {
            val isFirstTime = dataStore.data.firstOrNull()?.get(userLoggedInKey) != userAccount.id
            dataStore.edit {
                it[userLoggedInKey] = userAccount.id
            }
            if (isFirstTime) {
                donateCoins(OPEN_ACCOUNT_BONUS)
            }
        }

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

        mutex.withLock {
            val purchase = Purchase(
                date = System.currentTimeMillis(),
                productId = productId,
                price = price
            )
            userAccount1.transactions.add(purchase)

            updateBalanceInDataStore(userAccountId)

            println("PROCESSED PURCHASE, user owns: ${userAccount1.transactions}")
        }
    }

    suspend fun processReward(coins: Int) {
        mutex.withLock {
            userAccount1.run {
                addCoins(coins)
                updateBalanceInDataStore(id)
            }
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