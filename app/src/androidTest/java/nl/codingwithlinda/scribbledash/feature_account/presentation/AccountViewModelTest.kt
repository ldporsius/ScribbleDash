package nl.codingwithlinda.scribbledash.feature_account.presentation

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.test.AndroidTestAppModule
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class AccountManagerTest{

    private val appContext = ApplicationProvider.getApplicationContext<Application>()
    private val appModule = AndroidTestAppModule(appContext)
    private val accountManager = appModule.accountManager

    @After
    fun tearDown(){
        runBlocking {
            accountManager.clearCoinsInDatastore()
            appModule.datastore.edit {
                it.clear()
            }
        }
    }

    @Test
    fun testRegisterUserIsAtomic() = runBlocking{
        val balance = accountManager.balance(accountManager.userAccount1.id)
        assertEquals(0, balance)

        val jobs = (0 .. 100).map {
            launch {
                accountManager.setActiveUser(accountManager.userAccount1)
                val balance1 = accountManager.balance(accountManager.userAccount1.id)
                assertEquals(AccountManager.OPEN_ACCOUNT_BONUS, balance1)
            }
        }

        jobs.joinAll()

        val balance2 = accountManager.balance(accountManager.userAccount1.id)
        assertEquals(AccountManager.OPEN_ACCOUNT_BONUS, balance2)

    }

    @Test
    fun testCoinUpdatesAreAtomic() = runBlocking{
        val balance = accountManager.balance(accountManager.userAccount1.id)
        assertEquals(0, balance)

        val jobs = (0 until  100).map {index ->
            launch {
                accountManager.processReward(1)
                val balance1 = accountManager.balance(accountManager.userAccount1.id)
                println("BALANCE AT INDEX $index: $balance1")
                val expectedCoins = index + 1
                assertEquals(expectedCoins, balance1)
            }
        }

        jobs.joinAll()

        val balance2 = accountManager.balance(accountManager.userAccount1.id)
        assertEquals(100, balance2)

    }

    @Test
    fun purchasesUpdateBalanceAtomic(): Unit = runBlocking{
        val balance = accountManager.balance(accountManager.userAccount1.id)
        assertEquals(0, balance)

        val jobs = (1 .. 30).map{index ->
            launch{
                accountManager.setActiveUser(accountManager.userAccount1)
                assertEquals(AccountManager.OPEN_ACCOUNT_BONUS, accountManager.balance(accountManager.userAccount1.id))
                accountManager.processPurchase(
                    userAccountId = accountManager.userAccount1.id,
                    productId = "1",
                    price = 10
                )
                val expectedBalance = AccountManager.OPEN_ACCOUNT_BONUS - (index * 10)
                assertEquals(expectedBalance, accountManager.balance(accountManager.userAccount1.id))

            }
        }
        jobs.joinAll()

        val balance2 = accountManager.balance(accountManager.userAccount1.id)
        assertEquals(0, balance2)
    }
}