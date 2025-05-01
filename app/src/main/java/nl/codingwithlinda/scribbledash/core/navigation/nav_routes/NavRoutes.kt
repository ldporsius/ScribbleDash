package nl.codingwithlinda.scribbledash.core.navigation.nav_routes

import kotlinx.serialization.Serializable

@Serializable
object RootNavRoute: NavRoute

@Serializable
object HomeNavRoute : NavRoute

///////////////////////////////
@Serializable
object GameNavRoute : NavRoute

@Serializable
object GameRootNavRoute : NavRoute

@Serializable
object GameLevelNavRoute : NavRoute

@Serializable
object GameExampleNavRoute : NavRoute

/////////////One Round Wonder//////////////////////
@Serializable
object GameDrawNavRoute : NavRoute

@Serializable
object GameResultNavRoute : NavRoute
/////////////Speed draw /////////////////////////////
@Serializable
object SpeedDrawNavRoute : NavRoute

@Serializable
object SpeedDrawResultNavRoute : NavRoute

////////////////////////////////////
@Serializable
object StatisticsNavRoute : NavRoute