/**
 * Use case to check whether a given [PokemonType]
 * is currently loaded in local storage.
 */
interface IsTypeActiveUC {
    /**
     * Checks if the specified [PokemonType] is active by verifying its presence in the cached types.
     *
     * @param type The [PokemonType] to check.
     * @return A [Result] containing true if the type is active (cached), false otherwise.
     */
    suspend operator fun invoke(type: PokemonType): Result<Boolean>
}

internal class IsTypeActiveUCImpl(
    private val typeRepository: TypeRepository,
) : IsTypeActiveUC {

    override suspend operator fun invoke(type: PokemonType): Result<Boolean> = safeWrap {
        val cached = typeRepository.getCachedTypes().getOrThrow()
        cached.contains(type)
    }

}
