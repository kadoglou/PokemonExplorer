import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

/**
 * Use case for retrieving a list of Pokémon filtered
 * by type and query with pagination support.
 */
interface GetPokemonsUC {

    /**
     * Retrieves a paged list of Pokémon of the specified type matching the optional query.
     *
     * @param type The Pokémon type to filter by.
     * @param query Optional search query to filter Pokémon names.
     * @param page The page number to retrieve.
     * @param pageSize The number of items per page.
     * @return A [Result] containing a list of [Pokemon] on success or a failure.
     */
    suspend operator fun invoke(
        type: PokemonType,
        query: String = "",
        page: Int,
        pageSize: Int
    ): Result<List<Pokemon>>
}

internal class GetPokemonsUCImpl(
    private val typeRepo: TypeRepository,
    private val pokemonRepo: PokemonRepository,
) : GetPokemonsUC {

    override suspend fun invoke(
        type: PokemonType,
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<Pokemon>> = safeWrap {
        // Retrieve PokemonRefs (names) for the given type, query, and pagination.
        val names = typeRepo.getPagedPokemonRefs(type, query, page, pageSize).getOrThrow()

        // Use supervisorScope so that a failed Pokémon fetch does not cancel the entire batch.
        val results: List<Result<Pokemon>> = supervisorScope {
            names.map { name ->
                async { pokemonRepo.getByName(name) }  // returns Result<Pokemon>
            }.awaitAll()
        }

        // Collect the first error to throw a unified DomainFailure if any fetch failed.
        val firstError = results.firstOrNull { it is Result.Error } as? Result.Error
        if (firstError != null) throw DomainFailure(firstError)

        // Map successful results and return the list of Pokémon.
        results.map { it.successOrThrow().data }
    }
}