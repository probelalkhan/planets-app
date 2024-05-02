package dev.belalkhan.planetsapp.planets.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.planetsapp.data.remote.PlanetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetInfoViewModel @Inject constructor(
    private val repository: PlanetRepository,
) : ViewModel() {

    private val _planetInfo = MutableStateFlow<PlanetInfoState>(PlanetInfoState.Loading)
    val planetInfo = _planetInfo.asStateFlow()
    fun getPlanetInfo(id: String) = viewModelScope.launch {
        try {
            val planetInfo = repository.getPlanet(id)
            _planetInfo.value = PlanetInfoState.Success(planetInfo)
        } catch (e: Exception) {
            _planetInfo.value = PlanetInfoState.Error(e.message.toString())
        }
    }
}
