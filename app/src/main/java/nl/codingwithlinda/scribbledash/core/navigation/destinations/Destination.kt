package nl.codingwithlinda.scribbledash.core.navigation.destinations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import nl.codingwithlinda.scribbledash.core.navigation.nav_routes.NavRoute
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

data class Destination (
    val route: NavRoute,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val selectedColor: Color,
    val label: UiText
)