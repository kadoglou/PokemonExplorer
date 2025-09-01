package di

import PokemonType
import logic.SearchListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchListFeatureModule = module {

    viewModel { (initialType: PokemonType?) ->
        SearchListViewModel(
            initialType = initialType,
            getPokemonsUC = get(),
            typeRepository = get(),
            setActiveTypeUC = get()
        )
    }
}