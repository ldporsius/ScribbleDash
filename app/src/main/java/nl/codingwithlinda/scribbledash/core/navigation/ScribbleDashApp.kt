package nl.codingwithlinda.scribbledash.core.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.compose.navigation
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.navigation.nav_graphs.GameNavGraph
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.StatisticsNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.GameRootNavRoute
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.RootNavRoute
import nl.codingwithlinda.scribbledash.core.presentation.util.toUi
import nl.codingwithlinda.scribbledash.core.presentation.util.asString
import nl.codingwithlinda.scribbledash.feature_home.presentation.HomeScreen
import nl.codingwithlinda.scribbledash.feature_statistics.presentation.StatisticsScreen
import nl.codingwithlinda.scribbledash.ui.theme.backgroundLight

@Composable
fun ScribbleDashApp(
    appModule: AndroidAppModule
) {

    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = RootNavRoute,
        modifier = Modifier.safeContentPadding()
    ) {

        composable<RootNavRoute> {
            val navController = rememberNavController()
            val destinations = listOf(
                Destination(
                    route = StatisticsNavRoute,
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
            var selectedIndex by remember {
                mutableIntStateOf(destinations.indexOfFirst { it.route == HomeNavRoute })
            }
            Scaffold(
                containerColor = backgroundLight,
                bottomBar = {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.tertiary
                    ) {
                        destinations.forEachIndexed { index, destination ->

                            val selected = index == selectedIndex
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    selectedIndex = index
                                    navController.navigate(destination.route)
                                },
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
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = HomeNavRoute,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable<HomeNavRoute> {

                        HomeScreen(
                            gameModes = GameMode.entries.map { it.toUi() },
                            actionOnGameMode = {
                                rootNavController.navigate(GameNavRoute)
                            }
                        )
                    }

                    composable<StatisticsNavRoute> {
                        StatisticsScreen()
                    }

                }
            }
        }

        navigation<GameNavRoute>(
            startDestination = GameRootNavRoute
        ) {
            GameNavGraph(
                appModule = appModule,
                navToHome = {
                    rootNavController.navigate(RootNavRoute)
                }
            )
        }
    }
}
