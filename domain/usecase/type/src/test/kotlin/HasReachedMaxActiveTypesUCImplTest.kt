import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HasReachedMaxActiveTypesUCImplTest {

    // region Private fields

    private lateinit var typeRepository: TypeRepository
    private lateinit var useCaseImpl: HasReachedMaxActiveTypesUC

    // endregion

    // region Setup

    @Before
    fun setup() {
        typeRepository = mockk()
        useCaseImpl = HasReachedMaxActiveTypesUCImpl(typeRepository)
    }

    // endregion

    // region Tests

    @Test
    fun `should return false when cached size is less than limit`() = runTest {
        // given
        val cached = listOf(PokemonType.Fire)
        coEvery { typeRepository.getCachedTypes() } returns Result.Success(cached)

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Success)
        assertEquals(false, result.successOrThrow().data)
        coVerify(exactly = 1) { typeRepository.getCachedTypes() }
    }

    @Test
    fun `should return true when cached size equals the limit`() = runTest {
        // given
        val cached = listOf(PokemonType.Fire, PokemonType.Water)
        coEvery { typeRepository.getCachedTypes() } returns Result.Success(cached)

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Success)
        assertEquals(true, result.successOrThrow().data)
        coVerify(exactly = 1) { typeRepository.getCachedTypes() }
    }

    @Test
    fun `should return true when cached size is greater than limit`() = runTest {
        // given
        val cached = listOf(PokemonType.Fire, PokemonType.Water, PokemonType.Electric)
        coEvery { typeRepository.getCachedTypes() } returns Result.Success(cached)

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Success)
        assertEquals(true, result.successOrThrow().data)
        coVerify(exactly = 1) { typeRepository.getCachedTypes() }
    }

    @Test
    fun `should forward repository error`() = runTest {
        // given
        coEvery { typeRepository.getCachedTypes() } returns Result.Error.Local.CouldNotLoad

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Error.Local.CouldNotLoad)
        coVerify(exactly = 1) { typeRepository.getCachedTypes() }
    }

    // endregion

}