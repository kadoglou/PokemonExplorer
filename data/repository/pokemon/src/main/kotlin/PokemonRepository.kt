import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

internal class PokemonRepositoryImpl(
    private val localSource: PokemonLocalDataSource,
    private val remoteSource: PokemonRemoteDataSource,
) : PokemonRepository {

    override suspend fun getByName(name: String): Result<Pokemon> = safeWrap {
        // 1) Try in-memory cache
        PokemonCacheMemory.getPokemon(name)?.let { return@safeWrap it }

        // 2) Try local database
        val dbData = localSource.getByName(name).getOrThrow()
        if (dbData != null) {
            val domainData = dbData.toDomain()
            PokemonCacheMemory.savePokemon(domainData)
            return@safeWrap domainData
        }

        // 3) If neither cache or local database have the data, fetch from remote
        val fetchData = remoteSource.fetch(name).getOrThrow()

        // 4) Persist in Db and Cache
        localSource.upsert(fetchData.toEntity()).getOrThrow()
        PokemonCacheMemory.savePokemon(fetchData.toDomain())

        fetchData.toDomain()
    }

    override suspend fun deleteByNames(names: List<String>): Result<Unit> = safeWrap {
        localSource.deleteByNames(names).getOrThrow()
    }
}