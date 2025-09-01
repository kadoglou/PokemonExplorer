/**
 * Use case for setting a given [PokemonType] as active.
 */
interface SetActiveTypeUC {
    /**
     * Sets the specified Pokémon type as active.
     *
     * If the type is not active and the maximum number of active types has been reached,
     * it removes the oldest active type before fetching the new type from the repository.
     * If the type is already active, it updates its "last accessed" timestamp.
     *
     * @param type The Pokémon type to set as active.
     * @return A [Result] representing success or failure of the operation.
     */
    suspend operator fun invoke(type: PokemonType): Result<Unit>
}

internal class SetActiveTypeUCImpl(
    private val isTypeActiveUC: IsTypeActiveUC,
    private val hasReachedMaxActiveTypesUC: HasReachedMaxActiveTypesUC,
    private val removeOldestTypeUC: RemoveOldestTypeUC,
    private val typeRepository: TypeRepository,
) : SetActiveTypeUC {

    override suspend operator fun invoke(type: PokemonType): Result<Unit> = safeWrap {
        val isActive = isTypeActiveUC(type).getOrThrow()
        if (!isActive) {
            val reached = hasReachedMaxActiveTypesUC().getOrThrow()
            typeRepository.fetchType(type).getOrThrow()
            if (reached) {
                removeOldestTypeUC().getOrThrow()
            }
        } else {
            typeRepository.updateLastAccessed(type).getOrThrow()
        }
    }
}
