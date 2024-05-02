package dev.belalkhan.planetsapp.data.remote

import dev.belalkhan.planetsapp.network.NetworkResult
import dev.belalkhan.planetsapp.network.RequestHandler
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler
) : PlanetRepository {

    override suspend fun getPlanets(page: Int, limit: Int): List<PlanetDto> {
        val planets = requestHandler.get<PlanetsApiResponse>(
            urlPathSegments = listOf("planets"),
            queryParams = mapOf("page" to page, "limit" to limit)
        )
        return when (planets) {
            is NetworkResult.Error -> emptyList()
            is NetworkResult.Success -> {
                if (page <= planets.result.totalPages)
                    planets.result.results
                else
                    emptyList()
            }
        }
    }
}