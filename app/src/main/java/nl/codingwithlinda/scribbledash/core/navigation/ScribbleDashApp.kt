package nl.codingwithlinda.scribbledash.core.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.rememberNavController
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.navigation.destinations.Destination
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.HomeNavRoute
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.ChartNavRoute
import nl.codingwithlinda.scribbledash.core.presentation.util.asString

@Composable
fun ScribbleDashApp() {

    val destinations = listOf(
        Destination(
            route = ChartNavRoute,
            selectedIcon = ImageVector.vectorResource(R.drawable.chart),
            unselectedIcon = ImageVector.vectorResource(R.drawable.chart),
            label = UiText.StringResource(R.string.chart)
        ),
        Destination(
            route = HomeNavRoute,
            selectedIcon = ImageVector.vectorResource(R.drawable.home),
            unselectedIcon = Icons.Outlined.Home,
            label = UiText.StringResource(R.string.home)
        )
    )
    val rootNavController = rememberNavController()
    val currentDestination = rootNavController.currentBackStackEntryAsState().value

    var selectedIndex by remember {
        mutableIntStateOf( destinations.indexOfFirst { it.route == HomeNavRoute } )
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEachIndexed { index, destination ->

                    val selected = index == selectedIndex
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            selectedIndex = index
                            rootNavController.navigate(destination.route) },
                        icon = {
                            Image(
                                imageVector = destination.selectedIcon,
                                contentDescription = destination.label.asString(),
                                colorFilter = ColorFilter.tint(
                                    if (selected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            )
                        }
                    )
                }
            }
        }
    ) {paddingValues ->
        NavHost(
            navController = rootNavController,
            startDestination = HomeNavRoute,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable<HomeNavRoute> {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home"){
                    composable("home"){
                        Text(text = "Home")
                    }
                }
            }

            composable<ChartNavRoute> {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "chart"){
                    composable("chart"){
                        Text(text = "Chart")
                    }
                    }
                }
        }
    }
}