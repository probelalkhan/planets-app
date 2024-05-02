package dev.belalkhan.planetsapp.data.remote

interface PlanetRepository {
    suspend fun getPlanets(page: Int, limit: Int): List<PlanetDto>
}
