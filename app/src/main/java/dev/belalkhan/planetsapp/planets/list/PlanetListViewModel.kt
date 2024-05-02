package dev.belalkhan.planetsapp.planets.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.belalkhan.planetsapp.data.db.PlanetEntity
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(
    pager: Pager<Int, PlanetEntity>,
) : ViewModel() {

    val planetPagingFlow = pager.flow.cachedIn(viewModelScope)
}
