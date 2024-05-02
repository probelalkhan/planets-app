package dev.belalkhan.planetsapp

import android.content.Context
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.belalkhan.planetsapp.data.db.PlanetDatabase
import dev.belalkhan.planetsapp.data.db.PlanetEntity
import dev.belalkhan.planetsapp.data.remote.PlanetRemoteMediator
import dev.belalkhan.planetsapp.data.remote.PlanetRepository
import dev.belalkhan.planetsapp.data.remote.models.PlanetDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PlanetRemoteMediatorTest {

    private val mockRepository: PlanetRepository = mockk()
    private lateinit var db: PlanetDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            PlanetDatabase::class.java,
        ).build()
        val mockPlanets = List(20) { index -> PlanetDto(index.toString(), "Earth", "url") }
        coEvery { mockRepository.getPlanets(any(), any()) } returns mockPlanets
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = PlanetRemoteMediator(
            db,
            mockRepository,
        )
        val pagingState = PagingState<Int, PlanetEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10,
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached.not())
    }

    @Test
    fun checkEndOfPaginationReachedWhenNoMoreDataIsPresent() = runTest {
        coEvery { mockRepository.getPlanets(any(), any()) } returns listOf()
        val remoteMediator = PlanetRemoteMediator(
            db,
            mockRepository,
        )
        val pagingState = PagingState<Int, PlanetEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10,
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}
