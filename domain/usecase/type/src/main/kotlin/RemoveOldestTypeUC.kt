/**
 * Use case for removing the oldest cached [PokemonType]
 * along with its associated Pokémon data.
 */
interface RemoveOldestTypeUC {
    /**
     * Deletes the oldest cached [PokemonType] and all related Pokémon references.
     *
     * @return [Result] indicating success or failure of the operation.
     */
    suspend operator fun invoke(): Result<Unit>
}

internal class RemoveOldestTypeUCImpl(
    private val typeRepository: TypeRepository,
    private val pokemonRepository: PokemonRepository,
) : RemoveOldestTypeUC {

    override suspend operator fun invoke(): Result<Unit> = safeWrap {
        // Select the last cached type as the oldest type
        val oldestType = typeRepository.getCachedTypes().getOrThrow().last()
        // Retrieve all Pokémon tied to the oldest type
        val pokemonRefNames = typeRepository.getPokemonOnlyOfType(oldestType).getOrThrow()

        // First delete related Pokémon, then remove the type itself
        pokemonRepository.deleteByNames(pokemonRefNames).getOrThrow()
        typeRepository.delete(oldestType).getOrThrow()
    }
}
