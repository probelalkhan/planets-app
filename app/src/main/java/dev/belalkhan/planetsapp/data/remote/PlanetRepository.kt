package dev.belalkhan.planetsapp.data.remote

import dev.belalkhan.planetsapp.data.db.PlanetInfoEntity
import dev.belalkhan.planetsapp.data.remote.models.PlanetDto

interface PlanetRepository {
    suspend fun getPlanets(page: Int, limit: Int): List<PlanetDto>
    suspend fun getPlanet(id: String): PlanetInfoEntity
}
