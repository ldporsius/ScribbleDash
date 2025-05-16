package nl.codingwithlinda.scribbledash.core.navigation.nav_routes

import kotlinx.serialization.Serializable
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel

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


/////////////One Round Wonder//////////////////////
@Serializable
object OneRoundRootNavRoute : NavRoute
@Serializable
object OneRoundHostNavRoute : NavRoute


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

////////////////shop////////////////////////

@Serializable
object ShopNavRoute : NavRoute
@Serializable
object ShopRootNavRoute : NavRoute
@Serializable
object ShopHostNavRoute : NavRoute
