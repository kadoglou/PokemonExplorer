/**
 * Determines whether the maximum number of active Pokémon types has been reached.
 */
interface HasReachedMaxActiveTypesUC {
    /**
     * Checks if the cached types count meets or exceeds the configured limit.
     *
     * @return [Result] containing true if the maximum number of
     * active types has been reached, false otherwise.
     */
    suspend operator fun invoke(): Result<Boolean>
}

internal class HasReachedMaxActiveTypesUCImpl(
    private val typeRepository: TypeRepository,
) : HasReachedMaxActiveTypesUC {

    override suspend operator fun invoke(): Result<Boolean> = safeWrap {
        val cached = typeRepository.getCachedTypes().getOrThrow()
        cached.size >= AppConstantSettings.NUMBER_OF_ACTIVE_TYPES
    }

}
