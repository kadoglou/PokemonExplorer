package ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun searchListGraph(): NavGraphBuilder.() -> Unit = {
    composable(route = "search_list") {
        SearchListScreenRoot(null)
    }
}
