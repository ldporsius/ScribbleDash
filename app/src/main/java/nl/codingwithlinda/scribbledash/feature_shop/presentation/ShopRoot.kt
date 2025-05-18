package nl.codingwithlinda.scribbledash.feature_shop.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.receiveAsFlow
import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.data.shop.sales.CanvassesSalesManager
import nl.codingwithlinda.scribbledash.core.data.shop.sales.PensSalesManager
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.ShopNavRoute
import nl.codingwithlinda.scribbledash.core.presentation.util.ObserveAsEvents
import nl.codingwithlinda.scribbledash.feature_account.presentation.AccountViewModel
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopEvent

@Composable
fun ShopRoot(
    accountManager: AccountManager
) {
    val fakeUser = UserAccount(
        id = "1"
    )
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ShopNavRoute) {

        composable<ShopNavRoute> {

            val context = LocalContext.current
            val accountViewModel = viewModel<AccountViewModel>(
                factory = viewModelFactory {
                    initializer {
                        AccountViewModel(
                            accountManager = accountManager
                        )
                    }
                }
            )
            val viewModel = viewModel< ShopViewModel>(
                factory = viewModelFactory {
                    initializer {
                        ShopViewModel(
                            penSalesManager = PensSalesManager(accountManager),
                            canvasSalesManager = CanvassesSalesManager(accountManager),
                            userAccount = fakeUser
                        )
                    }
                }
            )
            ShopScreen(
                accountBalance = accountViewModel.balance.collectAsStateWithLifecycle().value,
                uiState = viewModel.uiState.value,
                onAction = viewModel::handleAction
            )

            ObserveAsEvents(viewModel.eventChannel.receiveAsFlow()){
                when(it){
                    is ShopEvent.BalanceInsufficient -> {
                        Toast.makeText(context, "You don't have enough coins to buy this item", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}