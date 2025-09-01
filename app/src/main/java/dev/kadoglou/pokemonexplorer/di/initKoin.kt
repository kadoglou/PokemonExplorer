package dev.kadoglou.pokemonexplorer.di


import di.connectivityModule
import di.databaseModule
import di.httpClientModule
import di.pokemonLocalDataSourceModule
import di.pokemonRemoteDataSourceModule
import di.pokemonRepositoryModule
import di.pokemonUsecaseModule
import di.searchListFeatureModule
import di.typeLocalDataSourceModule
import di.typeRemoteDataSourceModule
import di.typeRepositoryModule
import di.typeUsecaseModule
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

@OptIn(KoinInternalApi::class)
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            // Core
            httpClientModule,
            connectivityModule,
            databaseModule,

            // Local & Remote Sources
            pokemonLocalDataSourceModule,
            pokemonRemoteDataSourceModule,
            typeLocalDataSourceModule,
            typeRemoteDataSourceModule,

            // Repos
            pokemonRepositoryModule,
            typeRepositoryModule,

            // Usecases
            pokemonUsecaseModule,
            typeUsecaseModule,

            // Features
            searchListFeatureModule
        )
    }
}
