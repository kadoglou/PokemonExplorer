package di

import GetPokemonsUC
import GetPokemonsUCImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pokemonUsecaseModule = module {
    singleOf(::GetPokemonsUCImpl).bind<GetPokemonsUC>()
}