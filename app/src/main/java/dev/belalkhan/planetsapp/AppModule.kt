package dev.belalkhan.planetsapp

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.belalkhan.planetsapp.data.db.PlanetDatabase
import dev.belalkhan.planetsapp.data.db.PlanetEntity
import dev.belalkhan.planetsapp.data.remote.PlanetRemoteMediator
import dev.belalkhan.planetsapp.data.remote.PlanetRepository
import dev.belalkhan.planetsapp.data.remote.PlanetRepositoryImpl
import dev.belalkhan.planetsapp.network.MyHttpClient
import dev.belalkhan.planetsapp.network.PlanetsAppHttpClientBuilder
import dev.belalkhan.planetsapp.network.RequestHandler
import io.ktor.http.URLProtocol
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlanetDatabase(@ApplicationContext context: Context): PlanetDatabase {
        return Room.databaseBuilder(
            context,
            PlanetDatabase::class.java,
            "planet.db",
        ).build()
    }

    @Provides
    fun provideHttpClient(): MyHttpClient =
        PlanetsAppHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host(BuildConfig.PLANETS_APP_HOST)
            .build()

    @Provides
    fun provideRequestHandler(client: MyHttpClient): RequestHandler = RequestHandler(client)

    @Provides
    fun providesPlanetRepository(impl: PlanetRepositoryImpl): PlanetRepository = impl

    @Provides
    @Singleton
    fun providePlanetsPager(
        db: PlanetDatabase,
        repository: PlanetRepository,
    ): Pager<Int, PlanetEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
            ),
            remoteMediator = PlanetRemoteMediator(
                db = db,
                repository = repository,
            ),
            pagingSourceFactory = {
                db.planetDao.pagingSource()
            },
        )
    }
}
