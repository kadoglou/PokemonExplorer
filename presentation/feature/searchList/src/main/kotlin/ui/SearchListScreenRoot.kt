@file:OptIn(ExperimentalFoundationApi::class)

package ui

import PokemonType
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import logic.SearchListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun SearchListScreenRoot(
    initialType: PokemonType?,
    viewModel: SearchListViewModel = koinViewModel(
        parameters = { parametersOf(initialType) }
    )
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    SearchListScreen(state) { action ->
        viewModel.onAction(action)
    }
}