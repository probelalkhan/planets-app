package dev.belalkhan.planetsapp.planets.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.belalkhan.planetsapp.data.db.PlanetInfoEntity
import dev.belalkhan.planetsapp.ui.components.FullScreenProgressIndicator

@Composable
fun PlanetInfo(viewModel: PlanetInfoViewModel) {
    val state = viewModel.planetInfo.collectAsStateWithLifecycle()
    when (val value = state.value) {
        is PlanetInfoState.Error -> {}
        PlanetInfoState.Loading -> FullScreenProgressIndicator()
        is PlanetInfoState.Success -> Planet(value.planetInfo)
    }
}

@Composable
private fun Planet(info: PlanetInfoEntity) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
            text = info.properties.name,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Climate",
                    modifier = Modifier.weight(1f),
                )

                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = info.properties.climate,
                    modifier = Modifier.weight(1f),
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Diameter",
                    modifier = Modifier.weight(1f),
                )

                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = info.properties.diameter,
                    modifier = Modifier.weight(1f),
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Gravity",
                    modifier = Modifier.weight(1f),
                )

                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = info.properties.gravity,
                    modifier = Modifier.weight(1f),
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Terrain",
                    modifier = Modifier.weight(1f),
                )

                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = info.properties.terrain,
                    modifier = Modifier.weight(1f),
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "URL",
                    modifier = Modifier.weight(1f),
                )

                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = info.properties.url,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}
