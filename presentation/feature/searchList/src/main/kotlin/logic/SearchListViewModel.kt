package logic

import AppConstantSettings
import GetPokemonsUC
import PokemonType
import SetActiveTypeUC
import TypeRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import onError
import onSuccess
import toUi

internal class SearchListViewModel(
    initialType: PokemonType?,
    private val getPokemonsUC: GetPokemonsUC,
    private val typeRepository: TypeRepository,
    private val setActiveTypeUC: SetActiveTypeUC
) : ViewModel() {

    // region Private fields

    private val _state = MutableStateFlow(SearchListState(selectedType = initialType))
    val state = _state
        .onStart {
            observeSelectedType()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), _state.value)

    private var paginatorJob: Job? = null

    // endregion

    // region Public methods

    fun onAction(action: SearchListAction) {
        when (action) {
            is SearchListAction.OnTypeBadgeClick -> showTypeMenu()
            is SearchListAction.OnTypeMenuDismiss -> hideTypeMenu()
            is SearchListAction.OnTypeSelected -> changeActiveType(action.type)
            is SearchListAction.OnSearchQueryChange -> changeQuery(action.query)
            is SearchListAction.LoadNextPage -> paginationLoad()
            is SearchListAction.ClearError -> clearError()
        }
    }

    // endregion

    // region Private methods

    private fun showTypeMenu() {
        _state.update { it.copy(isTypeMenuVisible = true) }
    }

    private fun hideTypeMenu() {
        _state.update { it.copy(isTypeMenuVisible = false) }
    }

    private fun changeActiveType(type: PokemonType) {
        if (state.value.selectedType != type) {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch(Dispatchers.IO) {
                setActiveTypeUC(type).onError { err ->
                    _state.update { it.copy(error = err.toUi(), isLoading = false) }
                }
            }
        }
    }

    private fun changeQuery(query: String) {
        paginatorJob?.cancel()
        _state.update { it.copy(query = query, currentPage = 0) }
    }

    private fun paginationLoad() {
        paginatorJob?.cancel()
        paginatorJob = viewModelScope.launch(Dispatchers.IO) {
            val type = _state.value.selectedType ?: return@launch
            _state.update { it.copy(isLoading = true) }
            try {
                getPokemonsUC(
                    type,
                    _state.value.query,
                    _state.value.currentPage,
                    AppConstantSettings.POKEMON_LIST_PAGE_SIZE
                ).onSuccess { pokemons ->
                    if (_state.value.query.isNotBlank() && _state.value.currentPage == 0) {
                        _state.update {
                            it.copy(
                                pokemons = pokemons,
                                currentPage = it.currentPage + 1,
                                error = null
                            )
                        }
                    } else if (_state.value.query.isBlank() && _state.value.currentPage == 0) {
                        _state.update {
                            it.copy(
                                pokemons = pokemons,
                                currentPage = it.currentPage + 1,
                                error = null
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                pokemons = it.pokemons + pokemons,
                                currentPage = it.currentPage + 1,
                                error = null
                            )
                        }
                    }
                }.onError { err ->
                    _state.update { it.copy(error = err.toUi()) }
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun observeSelectedType() {
        typeRepository.observeActiveType()
            .distinctUntilChanged()
            .onEach { activeType ->
                if (activeType == null) {
                    _state.update { it.copy(isLoading = true) }
                    withContext(Dispatchers.IO) {
                        setActiveTypeUC(PokemonType.Fire).onError { err ->
                            _state.update { it.copy(error = err.toUi(), isLoading = false) }
                        }
                    }
                } else {
                    if (activeType != _state.value.selectedType) {
                        _state.update {
                            it.copy(
                                selectedType = activeType,
                                query = "",
                                pokemons = emptyList(),
                                currentPage = 0,
                                isLoading = true
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

    // endregion

}