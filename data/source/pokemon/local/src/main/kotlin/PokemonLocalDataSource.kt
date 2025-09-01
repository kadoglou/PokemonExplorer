import io.github.oshai.kotlinlogging.KotlinLogging

interface PokemonLocalDataSource {
    suspend fun upsert(pokemon: PokemonEntity): Result<Unit>
    suspend fun getByName(name: String): Result<PokemonEntity?>
    suspend fun deleteByNames(names: List<String>): Result<Unit>
}

private val logger = KotlinLogging.logger {}

internal class PokemonLocalDataSourceImpl(
    private val pokemonDao: PokemonDao
) : PokemonLocalDataSource {
    override suspend fun upsert(pokemon: PokemonEntity): Result<Unit> = safeWriteLocal {
        pokemonDao.upsert(pokemon)
    }

    override suspend fun getByName(name: String): Result<PokemonEntity?> = safeReadLocal {
        pokemonDao.get(name)
    }

    override suspend fun deleteByNames(names: List<String>): Result<Unit> = safeWriteLocal {
        pokemonDao.deleteMany(names)
    }
}