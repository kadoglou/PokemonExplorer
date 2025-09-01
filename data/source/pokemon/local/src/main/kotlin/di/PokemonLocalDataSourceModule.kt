package di

import PokemonLocalDataSource
import PokemonLocalDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pokemonLocalDataSourceModule = module {
    singleOf(::PokemonLocalDataSourceImpl).bind<PokemonLocalDataSource>()
}
