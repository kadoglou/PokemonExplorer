import androidx.room.Database
import androidx.room.RoomDatabase
import type.TypeDao
import type.TypeEntity
import type_pokemonRef.TypePokemonRefDao
import type_pokemonRef.TypePokemonRefEntity

@Database(
    entities = [
        PokemonEntity::class,
        TypeEntity::class,
        TypePokemonRefEntity::class,
    ],
    version = 8
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun typeDao(): TypeDao
    abstract fun typePokemonRefDao(): TypePokemonRefDao
}
