package dev.belalkhan.planetsapp

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.belalkhan.planetsapp.data.db.PlanetDatabase
import dev.belalkhan.planetsapp.data.db.PlanetEntity
import dev.belalkhan.planetsapp.data.db.PlanetInfoEntity
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTests {

    private lateinit var db: PlanetDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            PlanetDatabase::class.java,
        ).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writePlanetAndReadIt() = runTest {
        val planetEntities = listOf(
            PlanetEntity("1", "Earth", ""),
            PlanetEntity("2", "Earth", ""),
            PlanetEntity("3", "Earth", ""),
        )
        db.planetDao.upsertAll(planetEntities)
        val result = db.planetDao.pagingSource().load(PagingSource.LoadParams.Refresh(0, 3, false))
        assertThat((result as? PagingSource.LoadResult.Page?)?.data?.size, equalTo(3))
    }

    @Test
    @Throws(Exception::class)
    fun writePlanetInfoAndReadIt() = runTest {
        val planetInfo = PlanetInfoEntity("13", "1", "Earth", "", 1)
        db.planetInfoDao.insert(planetInfo)
        val result = db.planetInfoDao.getPlanetInfo("13")
        assertThat(result?.uid, equalTo("13"))
    }
}
