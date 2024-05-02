package dev.belalkhan.planetsapp.data.remote

import dev.belalkhan.planetsapp.data.db.PlanetDatabase
import dev.belalkhan.planetsapp.data.db.PlanetInfoEntity
import dev.belalkhan.planetsapp.data.remote.models.PlanetDto
import dev.belalkhan.planetsapp.data.remote.models.PlanetInfoApiResponse
import dev.belalkhan.planetsapp.data.remote.models.PlanetsApiResponse
import dev.belalkhan.planetsapp.data.toPlanetInfoEntity
import dev.belalkhan.planetsapp.network.NetworkResult
import dev.belalkhan.planetsapp.network.RequestHandler
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val requestHandler: RequestHandler,
    private val db: PlanetDatabase,
) : PlanetRepository {

    override suspend fun getPlanets(page: Int, limit: Int): List<PlanetDto> {
        val planets = requestHandler.get<PlanetsApiResponse>(
            urlPathSegments = listOf("planets"),
            queryParams = mapOf("page" to page, "limit" to limit),
        )
        return when (planets) {
            is NetworkResult.Error -> emptyList()
            is NetworkResult.Success -> {
                if (page <= planets.result.totalPages) {
                    planets.result.results
                } else {
                    emptyList()
                }
            }
        }
    }

    override suspend fun getPlanet(id: String): PlanetInfoEntity {
        val planet = db.planetInfoDao.getPlanetInfo(id)
        if (planet != null) {
            return planet
        }
        val planetInfo = requestHandler.get<PlanetInfoApiResponse>(urlPathSegments = listOf("planets", id))
        return when (planetInfo) {
            is NetworkResult.Error -> throw planetInfo.exception
            is NetworkResult.Success -> {
                val entity = planetInfo.result.result.toPlanetInfoEntity()
                db.planetInfoDao.insert(entity)
                entity
            }
        }
    }
}
