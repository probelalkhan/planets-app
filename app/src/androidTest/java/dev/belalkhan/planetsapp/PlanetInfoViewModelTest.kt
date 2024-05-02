package dev.belalkhan.planetsapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.belalkhan.planetsapp.data.db.PlanetInfoEntity
import dev.belalkhan.planetsapp.data.remote.PlanetRepository
import dev.belalkhan.planetsapp.planets.info.PlanetInfoState
import dev.belalkhan.planetsapp.planets.info.PlanetInfoViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlanetInfoViewModelTest {

    private val repository: PlanetRepository = mockk()
    private val viewModel = PlanetInfoViewModel(repository)

    @Test
    fun getPlanetInfoSuccess() {
        val planetInfoEntity: PlanetInfoEntity = mockk()
        coEvery { repository.getPlanet(any()) } returns planetInfoEntity
        viewModel.getPlanetInfo("1")
        assert(viewModel.planetInfo.value is PlanetInfoState.Success)
    }

    @Test
    fun getPlanetInfoError() {
        val exception = Exception("Error")
        coEvery { repository.getPlanet(any()) }.throws(exception)
        viewModel.getPlanetInfo("1")
        assert(viewModel.planetInfo.value is PlanetInfoState.Error)
    }
}
