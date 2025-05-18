package nl.codingwithlinda.scribbledash.feature_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount

class AccountViewModel(
    private val accountManager: AccountManager
): ViewModel() {

    fun loginUser(userAccount: UserAccount) = viewModelScope.launch{
        accountManager.setActiveUser(userAccount)
    }

    private val _balance = accountManager.observableBalanceActiveUser.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)
    val balance = _balance

    init {
        loginUser(accountManager.userAccount1)
    }
}