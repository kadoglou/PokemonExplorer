package di

import AppDatabase
import PokemonDao
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import type.TypeDao
import type_pokemonRef.TypePokemonRefDao

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "appDatabase.db"
        ).fallbackToDestructiveMigration(true).build()
    }

    single<PokemonDao> { get<AppDatabase>().pokemonDao() }
    single<TypeDao> { get<AppDatabase>().typeDao() }
    single<TypePokemonRefDao> { get<AppDatabase>().typePokemonRefDao() }
}