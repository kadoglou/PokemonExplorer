import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class IsTypeActiveUCImplTest {

    // region Private fields

    private lateinit var typeRepository: TypeRepository
    private lateinit var useCaseImpl: IsTypeActiveUC

    // endregion

    // region Setup

    @Before
    fun setup() {
        typeRepository = mockk()
        useCaseImpl = IsTypeActiveUCImpl(typeRepository)
    }
    // endregion

    // region Tests

    @Test
    fun `should return true when the given type is cached`() = runTest {
        // given
        val type = PokemonType.Fire
        val cached = listOf(PokemonType.Fire, PokemonType.Water)
        coEvery { typeRepository.getCachedTypes() } returns Result.Success(cached)

        // when
        val result = useCaseImpl(type)

        // then
        assertTrue(result is Result.Success)
        assertEquals(true, result.successOrThrow().data)
        coVerify(exactly = 1) { typeRepository.getCachedTypes() }
    }

    @Test
    fun `should return false when the given type is not cached`() = runTest {
        // given
        val type = PokemonType.Fire
        val cached = listOf(PokemonType.Water, PokemonType.Grass)
        coEvery { typeRepository.getCachedTypes() } returns Result.Success(cached)

        // when
        val result = useCaseImpl(type)

        // then
        assertTrue(result is Result.Success)
        assertEquals(false, result.successOrThrow().data)
        coVerify(exactly = 1) { typeRepository.getCachedTypes() }
    }

    @Test
    fun `should forward repository error`() = runTest {
        // given
        val type = PokemonType.Fire
        coEvery { typeRepository.getCachedTypes() } returns Result.Error.Local.CouldNotLoad

        // when
        val result = useCaseImpl(type)

        // then
        assertTrue(result is Result.Error.Local.CouldNotLoad)
        coVerify(exactly = 1) { typeRepository.getCachedTypes() }
    }

    // endregion
}