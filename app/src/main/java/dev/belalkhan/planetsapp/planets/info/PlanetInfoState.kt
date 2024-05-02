package dev.belalkhan.planetsapp.planets.info

import dev.belalkhan.planetsapp.data.db.PlanetInfoEntity

sealed class PlanetInfoState {
    data object Loading : PlanetInfoState()
    data class Success(val planetInfo: PlanetInfoEntity) : PlanetInfoState()
    data class Error(val error: String) : PlanetInfoState()
}
