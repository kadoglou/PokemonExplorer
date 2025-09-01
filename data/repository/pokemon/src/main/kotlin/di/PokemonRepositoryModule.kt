package di

import PokemonRepository
import PokemonRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pokemonRepositoryModule = module {
    singleOf(::PokemonRepositoryImpl).bind<PokemonRepository>()
}