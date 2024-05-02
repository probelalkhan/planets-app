package dev.belalkhan.planetsapp.planets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.belalkhan.planetsapp.planets.list.PlanetList

@Composable
fun PlanetsAppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.PlanetList.route
    ) {

        composable(Routes.PlanetList.route) {
            PlanetList(hiltViewModel())
        }

        composable(Routes.PlanetInfo.route) {
            Text("Planet Details")
        }
    }
}