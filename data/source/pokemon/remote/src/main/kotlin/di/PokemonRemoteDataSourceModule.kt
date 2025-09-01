package di

import PokemonRemoteDataSource
import PokemonRemoteDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pokemonRemoteDataSourceModule = module {
    singleOf(::PokemonRemoteDataSourceImpl).bind<PokemonRemoteDataSource>()
}
