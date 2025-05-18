package nl.codingwithlinda.scribbledash.feature_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount

class AccountViewModel(
    private val accountManager: AccountManager
): ViewModel() {

    fun loginUser(userAccount: UserAccount){
        accountManager.setActiveUser(userAccount)
    }

    private val _balance = MutableStateFlow(0)
    val balance = _balance.asStateFlow()

    init {
        accountManager.observableBalanceActiveUser.onEach { balance ->
            _balance.update {
                balance
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)
    }
}