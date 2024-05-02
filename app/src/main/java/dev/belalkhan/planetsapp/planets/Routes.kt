package dev.belalkhan.planetsapp.planets

sealed class Routes(val route: String) {
    data object PlanetList : Routes("planet_list")
    data object PlanetInfo : Routes("planet/{planet_id}") {
        const val KEY_PLANET_ID = "planet_id"
        fun createRoute(planetId: String) = "planet/$planetId"
    }
}
