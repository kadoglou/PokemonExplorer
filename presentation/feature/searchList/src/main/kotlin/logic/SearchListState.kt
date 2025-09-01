package logic

import Pokemon
import PokemonType
import UiError

internal data class SearchListState(
    val selectedType: PokemonType?,
    val query: String = "",
    val pokemons: List<Pokemon> = emptyList(),
    val isTypeMenuVisible: Boolean = false,
    val isLoading: Boolean = false,
    val currentPage: Int = 0,
    val error: UiError? = null,
)