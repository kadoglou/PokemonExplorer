import kotlinx.coroutines.flow.Flow
import type.TypeDao
import type.TypeEntity
import type_pokemonRef.TypePokemonRefDao
import type_pokemonRef.TypePokemonRefEntity

interface TypeLocalDataSource {
    suspend fun upsert(type: TypeEntity): Result<Unit>
    suspend fun getCachedTypes(): Result<List<TypeEntity>>
    suspend fun updateLastAccessed(typeName: String, timestamp: Long): Result<Unit>
    suspend fun deleteByName(typeName: String): Result<Unit>
    fun observeActiveType(): Flow<TypeEntity?>

    suspend fun insertRefs(refs: List<TypePokemonRefEntity>): Result<Unit>
    suspend fun getPaged(
        typeName: String,
        query: String,
        limit: Int,
        offset: Int
    ): Result<List<String>>

    suspend fun getCountForPrefix(typeName: String, query: String): Result<Int>
    suspend fun getPokemonsOnlyOfType(typeName: String): Result<List<String>>
}

internal class TypeLocalDataSourceImpl(
    private val typeDao: TypeDao,
    private val typePokemonRefDao: TypePokemonRefDao
) : TypeLocalDataSource {

    // region Type

    override suspend fun upsert(type: TypeEntity): Result<Unit> =
        safeWriteLocal { typeDao.upsert(type) }

    override suspend fun getCachedTypes(): Result<List<TypeEntity>> =
        safeReadLocal { typeDao.getCachedTypes() }

    override suspend fun updateLastAccessed(typeName: String, timestamp: Long): Result<Unit> =
        safeWriteLocal { typeDao.updateLastAccessed(typeName, timestamp) }

    override suspend fun deleteByName(typeName: String): Result<Unit> =
        safeWriteLocal { typeDao.deleteByName(typeName) }

    override fun observeActiveType(): Flow<TypeEntity?> =
        typeDao.observeActiveType()

    // endregion

    // region TypePokemonRef

    override suspend fun insertRefs(refs: List<TypePokemonRefEntity>): Result<Unit> =
        safeWriteLocal { typePokemonRefDao.insertAll(refs) }

    override suspend fun getPaged(
        typeName: String,
        query: String,
        limit: Int,
        offset: Int
    ): Result<List<String>> =
        safeReadLocal { typePokemonRefDao.getPagedNamesByType(typeName, query, limit, offset) }

    override suspend fun getCountForPrefix(
        typeName: String,
        query: String,
    ): Result<Int> =
        safeReadLocal { typePokemonRefDao.countNamesByTypePrefix(typeName, query) }

    override suspend fun getPokemonsOnlyOfType(typeName: String): Result<List<String>> =
        safeReadLocal { typePokemonRefDao.getPokemonsOnlyOfType(typeName) }

    // endregion
}