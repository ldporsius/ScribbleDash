package nl.codingwithlinda.scribbledash.core.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.ShopRootNavRoute
import nl.codingwithlinda.scribbledash.feature_shop.presentation.ShopRoot

fun NavGraphBuilder.shopNavGraph(){

        composable<ShopRootNavRoute>{
            ShopRoot()

            }


}