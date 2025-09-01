package logic

import PokemonType

internal sealed interface SearchListAction {
    data object OnTypeBadgeClick : SearchListAction
    data object OnTypeMenuDismiss : SearchListAction
    data object LoadNextPage : SearchListAction
    data class OnTypeSelected(val type: PokemonType) : SearchListAction
    data class OnSearchQueryChange(val query: String) : SearchListAction
    data object ClearError : SearchListAction
}