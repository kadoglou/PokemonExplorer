import kotlinx.coroutines.flow.Flow

interface TypeRepository {

    /**
     * Ensures the Pokémon refs for a type are loaded.
     * - If cached locally → skip fetch.
     * - Otherwise fetch from remote, cache refs + type data.
     */
    suspend fun fetchType(type: PokemonType): Result<Unit>

    fun observeActiveType(): Flow<PokemonType?>

    /**
     * Returns Pokémon names for the given type.
     * Supports paging via limit + offset.
     */
    suspend fun getPagedPokemonRefs(
        type: PokemonType,
        query: String = "",
        page: Int,
        pageSize: Int
    ): Result<List<String>>

    suspend fun getCountForPrefix(
        type: PokemonType,
        query: String,
    ): Result<Int>

    /**
     * Returns Pokémon names that exclusively belong to the given type.
     * Useful when deleting a type to identify Pokémon that should also be removed.
     */
    suspend fun getPokemonOnlyOfType(typeName: PokemonType): Result<List<String>>

    /**
     * Returns all cached types sorted by last accessed.
     */
    suspend fun getCachedTypes(): Result<List<PokemonType>>

    /**
     * Updates the last access timestamp of a type.
     */
    suspend fun updateLastAccessed(type: PokemonType): Result<Unit>

    /**
     * Deletes a specific type.
     */
    suspend fun delete(type: PokemonType): Result<Unit>
}