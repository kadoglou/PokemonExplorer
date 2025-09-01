import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RemoveOldestTypeUCImplTest {

    // region Private fields

    private lateinit var typeRepository: TypeRepository
    private lateinit var pokemonRepository: PokemonRepository
    private lateinit var useCaseImpl: RemoveOldestTypeUC

    // endregion

    // region Setup

    @Before
    fun setup() {
        typeRepository = mockk()
        pokemonRepository = mockk()
        useCaseImpl = RemoveOldestTypeUCImpl(
            typeRepository = typeRepository,
            pokemonRepository = pokemonRepository
        )
    }

    // endregion

    // region Tests

    @Test
    fun `should remove oldest type and its pokemons when cache not empty`() = runTest {
        // given
        val cached = listOf(
            PokemonType.Water,
            PokemonType.Fire,
            PokemonType.Grass
        )
        val oldest = PokemonType.Grass
        val names = listOf("bulbasaur", "oddish")

        coEvery { typeRepository.getCachedTypes() } returns Result.Success(cached)
        coEvery { typeRepository.getPokemonOnlyOfType(oldest) } returns Result.Success(names)
        coEvery { pokemonRepository.deleteByNames(names) } returns Result.Success(Unit)
        coEvery { typeRepository.delete(oldest) } returns Result.Success(Unit)

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Success)
        result.successOrThrow() // just ensures it's Success(Unit)

        coVerify(exactly = 1) {
            typeRepository.getCachedTypes()
            typeRepository.getPokemonOnlyOfType(oldest)
            pokemonRepository.deleteByNames(names)
            typeRepository.delete(oldest)
        }
    }

    @Test
    fun `should forward error when cached types retrieval fails`() = runTest {
        // given
        coEvery { typeRepository.getCachedTypes() } returns Result.Error.Local.CouldNotLoad

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Error.Local.CouldNotLoad)

        coVerify(exactly = 1) { typeRepository.getCachedTypes() }
        coVerify(exactly = 0) {
            typeRepository.getPokemonOnlyOfType(any())
            pokemonRepository.deleteByNames(any())
            typeRepository.delete(any())
        }
    }

    @Test
    fun `should stop and return error if deleting pokemons fails, without deleting type`() =
        runTest {
            // given
            val cached = listOf(PokemonType.Fire)
            val oldest = PokemonType.Fire
            val names = listOf("charmander")

            coEvery { typeRepository.getCachedTypes() } returns Result.Success(cached)
            coEvery { typeRepository.getPokemonOnlyOfType(oldest) } returns Result.Success(names)
            coEvery { pokemonRepository.deleteByNames(names) } returns Result.Error.Local.CouldNotSave
            // typeRepository.delete should not be called

            // when
            val result = useCaseImpl()

            // then
            assertTrue(result is Result.Error.Local.CouldNotSave)

            coVerify(exactly = 1) {
                typeRepository.getCachedTypes()
                typeRepository.getPokemonOnlyOfType(oldest)
                pokemonRepository.deleteByNames(names)
            }
            coVerify(exactly = 0) { typeRepository.delete(any()) }
        }

    // endregion
}