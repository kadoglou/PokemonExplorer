package dev.kadoglou.pokemonexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ui.searchListGraph

@Composable
fun AppRootNavigation(
    rootNavController: NavHostController,
) {
    NavHost(
        navController = rootNavController,
        startDestination = "search_list",
    ) {
        searchListGraph().invoke(this)
    }
}

