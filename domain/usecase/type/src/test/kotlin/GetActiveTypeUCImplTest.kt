import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetActiveTypeUCImplTest {

    // region Private fields

    private lateinit var typeRepo: TypeRepository
    private lateinit var useCaseImpl: GetActiveTypeUC

    // endregion

    // region Setup

    @Before
    fun setup() {
        typeRepo = mockk()
        useCaseImpl = GetActiveTypeUCImpl(typeRepo)
    }

    // endregion

    // region Tests

    @Test
    fun `should return first cached type when cache is not empty`() = runTest {
        // given
        val cached = listOf(PokemonType.Fire, PokemonType.Water)
        coEvery { typeRepo.getCachedTypes() } returns Result.Success(cached)

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Success)
        val data = result.successOrThrow().data
        assertEquals(PokemonType.Fire, data)
        coVerify(exactly = 1) { typeRepo.getCachedTypes() }
    }

    @Test
    fun `should return null when cache is empty`() = runTest {
        // given
        coEvery { typeRepo.getCachedTypes() } returns Result.Success(emptyList())

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Success)
        val data = result.successOrThrow().data
        assertEquals(null, data)
        coVerify(exactly = 1) { typeRepo.getCachedTypes() }
    }

    @Test
    fun `should forward error when repository fails`() = runTest {
        // given
        coEvery { typeRepo.getCachedTypes() } returns Result.Error.Local.CouldNotLoad

        // when
        val result = useCaseImpl()

        // then
        assertTrue(result is Result.Error.Local.CouldNotLoad)
        coVerify(exactly = 1) { typeRepo.getCachedTypes() }
    }

    // endregion

}