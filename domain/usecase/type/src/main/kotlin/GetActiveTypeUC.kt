/**
 * Use case to retrieve the latest accessed [PokemonType],
 * or null if no initial type has been specified.
 *
 */
interface GetActiveTypeUC {
    /**
     * Retrieves the most recently accessed Pokémon type.
     *
     * @return a [Result] containing the most recently used [PokemonType], or null if none is available.
     */
    suspend operator fun invoke(): Result<PokemonType?>
}

internal class GetActiveTypeUCImpl(
    private val typeRepository: TypeRepository,
) : GetActiveTypeUC {

    override suspend operator fun invoke(): Result<PokemonType?> = safeWrap {
        val cachedTypes = typeRepository.getCachedTypes().getOrThrow()
        cachedTypes.firstOrNull()
    }

}