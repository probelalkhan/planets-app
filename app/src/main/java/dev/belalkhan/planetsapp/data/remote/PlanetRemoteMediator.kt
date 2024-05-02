package dev.belalkhan.planetsapp.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.belalkhan.planetsapp.data.db.PlanetDatabase
import dev.belalkhan.planetsapp.data.db.PlanetEntity
import dev.belalkhan.planetsapp.data.toPlanetEntity
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PlanetRemoteMediator(
    private val db: PlanetDatabase,
    private val repository: PlanetRepository
) : RemoteMediator<Int, PlanetEntity>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlanetEntity>
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    1
                } else {
                    (lastItem.uid.toInt() / state.config.pageSize) + 1
                }
            }
        }

        val planets = repository.getPlanets(
            page = loadKey,
            limit = state.config.pageSize
        )

        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                db.planetDao.clearAll()
            }
            val planetEntities = planets.map { it.toPlanetEntity() }
            db.planetDao.upsertAll(planetEntities)
        }

        return MediatorResult.Success(
            endOfPaginationReached = planets.isEmpty()
        )
    }
}