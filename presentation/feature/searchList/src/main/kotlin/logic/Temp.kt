//package logic
//
//import PokemonDetails
//import PokemonDetailsRepository
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.distinctUntilChanged
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.flow.onStart
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import logic.SearchListAction.OnSearchQueryChange
//import logic.SearchListAction.OnTypeBadgeClick
//import logic.SearchListAction.OnTypeMenuDismiss
//import logic.SearchListAction.OnTypeSelected
//
//internal class SearchListViewModel(
//    private val pokemonDetailsRepository: PokemonDetailsRepository,
//) : ViewModel() {
//
//    private var cachedPokemons: List<PokemonDetails> = emptyList()
//    private var searchJob: Job? = null
//
//    private var lastQuery: String = ""
//    private var lastFiltered: List<PokemonDetails> = emptyList()
//
//    private val _state = MutableStateFlow(SearchListState())
//
//    val state = _state
//        .onStart {
//            // Observe query changes (debounced)
//            observeSearchQuery()
//            // Observe type changes
//            observeSelectedType()
//        }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000L),
//            _state.value
//        )
//
//
//    fun onAction(action: SearchListAction) {
//        when (action) {
//            is OnTypeBadgeClick -> {
//                showTypeMenu()
//            }
//
//            is OnTypeMenuDismiss -> {
//                hideTypeMenu()
//            }
//
//            is OnTypeSelected -> {
//                _state.update { it.copy(selectedType = action.type) }
//            }
//
//            is OnSearchQueryChange -> {
//                _state.update { it.copy(query = action.query) }
//            }
//        }
//    }
//
//    private fun observeSearchQuery() {
//        state
//            .map { it.query }
//            .distinctUntilChanged()
//            .onEach { newQueryRaw ->
//                val newQuery = newQueryRaw.trim()
//                // cancel previous debounce job
//                searchJob?.cancel()
//                searchJob = viewModelScope.launch {
//                    delay(350)
//                    val prev = lastQuery
//                    val source: List<PokemonDetails> =
//                        if (newQuery.lowercase().startsWith(prev)) lastFiltered else cachedPokemons
//
//                    val result = if (newQuery.isBlank()) {
//                        cachedPokemons
//                    } else {
//                        val q = newQuery.lowercase()
//                        source.filter { it.name.startsWith(q) }
//                    }
//
//                    lastQuery = newQuery.lowercase()
//                    lastFiltered = result
//                    _state.update { it.copy(pokemons = result) }
//                }
//            }
//            .launchIn(viewModelScope)
//    }
//
//    private fun observeSelectedType() {
//        state
//            .map { it.selectedType }
//            .distinctUntilChanged()
//            .onEach { type ->
//                // reset incremental cache
//                lastQuery = ""
//                lastFiltered = emptyList()
//                searchJob?.cancel()
//                viewModelScope.launch {
//                    val pokemons = pokemonDetailsRepository.getPokemonDetails(type)
//                    cachedPokemons = pokemons
//                    lastFiltered = cachedPokemons
//                    _state.update { it.copy(pokemons = pokemons) }
//                }
//            }
//            .launchIn(viewModelScope)
//    }
//
//    private fun showTypeMenu() {
//        _state.update { it.copy(isTypeMenuVisible = true) }
//    }
//
//    private fun hideTypeMenu() {
//        _state.update { it.copy(isTypeMenuVisible = false) }
//    }
//
//}