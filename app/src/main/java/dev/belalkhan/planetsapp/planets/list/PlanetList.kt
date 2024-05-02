package dev.belalkhan.planetsapp.planets.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.belalkhan.planetsapp.data.db.PlanetEntity
import dev.belalkhan.planetsapp.planets.planetIcon

@Composable
fun PlanetList(viewModel: PlanetListViewModel, onPlanetClick: (PlanetEntity) -> Unit) {
    val planets = viewModel.planetPagingFlow.collectAsLazyPagingItems()
    val refreshing = planets.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing, { planets.refresh() })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(
                count = planets.itemCount,
                key = planets.itemKey { it.uid },
            ) { index ->
                val planet = planets[index]
                if (planet != null) {
                    PlanetItem(planet = planet) { onPlanetClick(planet) }
                }
            }
            item {
                if (planets.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun PlanetItem(planet: PlanetEntity, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable { onClick() },
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(182.dp),
            painter = painterResource(id = planetIcon),
            contentDescription = planet.name,
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
            text = planet.name,
        )
    }
}
