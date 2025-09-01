import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TypeRepositoryImpl(
    private val localSource: TypeLocalDataSource,
    private val remoteSource: TypeRemoteDataSource,
) : TypeRepository {

    override suspend fun fetchType(type: PokemonType): Result<Unit> = safeWrap {
        val dto = remoteSource.fetch(type.apiName).getOrThrow()

        val now = System.currentTimeMillis()
        localSource.upsert(dto.toEntity(now)).getOrThrow()

        val refs = dto.toRefs()
        if (refs.isNotEmpty()) {
            localSource.insertRefs(refs).getOrThrow()
        }
    }

    override fun observeActiveType(): Flow<PokemonType?> {
        return localSource.observeActiveType().map { typeEntity ->
            if (typeEntity == null) null else PokemonType.fromApiName(typeEntity.name)
        }
    }

    override suspend fun getPagedPokemonRefs(
        type: PokemonType,
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<String>> = safeWrap {
        val offset = page * pageSize
        localSource.getPaged(
            typeName = type.apiName,
            query = query,
            limit = pageSize,
            offset = offset
        ).getOrThrow()
    }

    override suspend fun getCountForPrefix(
        type: PokemonType,
        query: String,
    ): Result<Int> = safeWrap {
        localSource.getCountForPrefix(type.apiName, query).getOrThrow()
    }

    override suspend fun getPokemonOnlyOfType(type: PokemonType): Result<List<String>> = safeWrap {
        localSource.getPokemonsOnlyOfType(type.apiName).getOrThrow()
    }

    override suspend fun getCachedTypes(): Result<List<PokemonType>> = safeWrap {
        localSource.getCachedTypes().getOrThrow().map { PokemonType.fromApiName(it.name) }
    }

    override suspend fun updateLastAccessed(type: PokemonType): Result<Unit> = safeWrap {
        localSource.updateLastAccessed(type.apiName, System.currentTimeMillis()).getOrThrow()
    }

    override suspend fun delete(type: PokemonType): Result<Unit> = safeWrap {
        localSource.deleteByName(type.apiName).getOrThrow()
    }

}