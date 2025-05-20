package nl.codingwithlinda.scribbledash.feature_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount

class AccountViewModel(
    private val accountManager: AccountManager
): ViewModel() {

    private fun loginUser(userAccount: UserAccount) = viewModelScope.launch{
        accountManager.setActiveUser(userAccount)
    }

    val balance = accountManager.observableBalanceActiveUser
        .catch {
            println("ERROR: ${it.message}")
        }.retry()

    init {
        loginUser(accountManager.userAccount1)

        viewModelScope.launch {

            val coins = balance.firstOrNull() ?: 0
            if (coins < 300) {
                accountManager.donateCoins(300 - coins)
            }
        }

    }
}