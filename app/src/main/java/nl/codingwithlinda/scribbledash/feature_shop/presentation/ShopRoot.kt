package nl.codingwithlinda.scribbledash.feature_shop.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.data.shop.sales.CanvassesSalesManager
import nl.codingwithlinda.scribbledash.core.data.shop.sales.PensSalesManager
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.ShopNavRoute

@Composable
fun ShopRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ShopNavRoute) {

        composable<ShopNavRoute> {

            val viewModel = viewModel< ShopViewModel>(
                factory = viewModelFactory {
                    initializer {
                        ShopViewModel(
                            penSalesManager = PensSalesManager(),
                            canvasSalesManager = CanvassesSalesManager()
                        )
                    }
                }
            )
            ShopScreen(
                uiState = viewModel.uiState.value
            )
        }

    }
}