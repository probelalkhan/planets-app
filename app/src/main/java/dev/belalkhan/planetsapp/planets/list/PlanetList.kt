package dev.belalkhan.planetsapp.planets.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.belalkhan.planetsapp.data.db.PlanetEntity

@Composable
fun PlanetList(viewModel: PlanetListViewModel) {
    val planets = viewModel.planetPagingFlow.collectAsLazyPagingItems()
    val refreshing = planets.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing, { planets.refresh() })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                count = planets.itemCount,
                key = planets.itemKey { it.uid },
            ) { index ->
                val planet = planets[index]
                if (planet != null) {
                    PlanetItem(planet = planet)
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
fun PlanetItem(planet: PlanetEntity) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.titleLarge,
                text = planet.name
            )
        }
    }
}