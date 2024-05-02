package dev.belalkhan.planetsapp.planets

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.belalkhan.planetsapp.planets.info.PlanetInfo
import dev.belalkhan.planetsapp.planets.info.PlanetInfoViewModel
import dev.belalkhan.planetsapp.planets.list.PlanetList

@Composable
fun PlanetsAppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.PlanetList.route,
    ) {
        composable(Routes.PlanetList.route) {
            PlanetList(
                viewModel = hiltViewModel(),
                onPlanetClick = { planet ->
                    navHostController.navigate(Routes.PlanetInfo.createRoute(planet.uid))
                },
            )
        }

        composable(
            route = Routes.PlanetInfo.route,
            arguments = listOf(
                navArgument(Routes.PlanetInfo.KEY_PLANET_ID) {
                    type = NavType.StringType
                },
            ),
        ) {
            val planetId = it.arguments?.getString(Routes.PlanetInfo.KEY_PLANET_ID) ?: "1"
            val viewModel = hiltViewModel<PlanetInfoViewModel>()
            viewModel.getPlanetInfo(planetId)
            PlanetInfo(viewModel)
        }
    }
}
