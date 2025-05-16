package nl.codingwithlinda.scribbledash.feature_shop.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.ShopHostNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.ShopNavRoute

@Composable
fun ShopRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ShopNavRoute) {

        composable<ShopNavRoute> {
            ShopScreen()
        }

    }
}