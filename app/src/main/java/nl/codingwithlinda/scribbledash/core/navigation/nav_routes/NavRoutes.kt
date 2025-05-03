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

/////////////Endless mode///////////////////////
@Serializable
object EndlessRootNavRoute : NavRoute
@Serializable
object EndlessHostNavRoute : NavRoute

@Serializable
object EndlessDrawNavRoute : NavRoute

@Serializable
object EndlessResultNavRoute : NavRoute

@Serializable
object EndlessGameOverNavRoute : NavRoute

@Serializable
object StatisticsNavRoute : NavRoute