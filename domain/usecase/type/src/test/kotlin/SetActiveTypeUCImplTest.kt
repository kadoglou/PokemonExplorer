import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SetActiveTypeUCImplTest {

    // region Private fields

    private lateinit var isTypeActiveUC: IsTypeActiveUC
    private lateinit var hasReachedMaxActiveTypesUC: HasReachedMaxActiveTypesUC
    private lateinit var removeOldestTypeUC: RemoveOldestTypeUC
    private lateinit var typeRepository: TypeRepository
    private lateinit var useCaseImpl: SetActiveTypeUC

    // endregion

    // region Setup

    @Before
    fun setup() {
        isTypeActiveUC = mockk()
        hasReachedMaxActiveTypesUC = mockk()
        removeOldestTypeUC = mockk()
        typeRepository = mockk()
        useCaseImpl = SetActiveTypeUCImpl(
            isTypeActiveUC = isTypeActiveUC,
            hasReachedMaxActiveTypesUC = hasReachedMaxActiveTypesUC,
            removeOldestTypeUC = removeOldestTypeUC,
            typeRepository = typeRepository
        )
    }

    // endregion

    // region Tests

    @Test
    fun `should fetch type and remove oldest when not active and limit reached`() = runTest {
        // given
        val type = PokemonType.Fire
        coEvery { isTypeActiveUC(type) } returns Result.Success(false)
        coEvery { hasReachedMaxActiveTypesUC() } returns Result.Success(true)
        coEvery { typeRepository.fetchType(type) } returns Result.Success(Unit)
        coEvery { removeOldestTypeUC() } returns Result.Success(Unit)

        // when
        val result = useCaseImpl(type)

        // then
        assertTrue(result is Result.Success)
        result.successOrThrow() // Success(Unit)

        coVerifyOrder {
            isTypeActiveUC(type)
            hasReachedMaxActiveTypesUC()
            typeRepository.fetchType(type)
            removeOldestTypeUC()
        }
        coVerify(exactly = 0) { typeRepository.updateLastAccessed(any()) }
    }

    @Test
    fun `should fetch type and NOT remove oldest when not active and limit NOT reached`() =
        runTest {
            // given
            val type = PokemonType.Water
            coEvery { isTypeActiveUC(type) } returns Result.Success(false)
            coEvery { hasReachedMaxActiveTypesUC() } returns Result.Success(false)
            coEvery { typeRepository.fetchType(type) } returns Result.Success(Unit)

            // when
            val result = useCaseImpl(type)

            // then
            assertTrue(result is Result.Success)
            result.successOrThrow()

            coVerifyOrder {
                isTypeActiveUC(type)
                hasReachedMaxActiveTypesUC()
                typeRepository.fetchType(type)
            }
            coVerify(exactly = 0) {
                removeOldestTypeUC()
                typeRepository.updateLastAccessed(any())
            }
        }

    @Test
    fun `should update last accessed when type already active`() = runTest {
        // given
        val type = PokemonType.Grass
        coEvery { isTypeActiveUC(type) } returns Result.Success(true)
        coEvery { typeRepository.updateLastAccessed(type) } returns Result.Success(Unit)

        // when
        val result = useCaseImpl(type)

        // then
        assertTrue(result is Result.Success)
        result.successOrThrow()

        coVerifyOrder {
            isTypeActiveUC(type)
            typeRepository.updateLastAccessed(type)
        }
        coVerify(exactly = 0) {
            hasReachedMaxActiveTypesUC()
            typeRepository.fetchType(any())
            removeOldestTypeUC()
        }
    }

    @Test
    fun `should propagate error when isTypeActiveUC fails`() = runTest {
        // given
        val type = PokemonType.Fire
        coEvery { isTypeActiveUC(type) } returns Result.Error.Local.CouldNotLoad

        // when
        val result = useCaseImpl(type)

        // then
        assertTrue(result is Result.Error.Local.CouldNotLoad)

        coVerify(exactly = 1) { isTypeActiveUC(type) }
        coVerify(exactly = 0) {
            hasReachedMaxActiveTypesUC()
            typeRepository.fetchType(any())
            typeRepository.updateLastAccessed(any())
            removeOldestTypeUC()
        }
    }

    @Test
    fun `should propagate error when fetchType fails`() = runTest {
        // given
        val type = PokemonType.Fire
        coEvery { isTypeActiveUC(type) } returns Result.Success(false)
        coEvery { hasReachedMaxActiveTypesUC() } returns Result.Success(false)
        coEvery { typeRepository.fetchType(type) } returns Result.Error.Remote.Unavailable

        // when
        val result = useCaseImpl(type)

        // then
        assertTrue(result is Result.Error.Remote.Unavailable)

        coVerifyOrder {
            isTypeActiveUC(type)
            hasReachedMaxActiveTypesUC()
            typeRepository.fetchType(type)
        }
        coVerify(exactly = 0) {
            removeOldestTypeUC()
            typeRepository.updateLastAccessed(any())
        }
    }

    @Test
    fun `should propagate error when removeOldestType fails`() = runTest {
        // given
        val type = PokemonType.Fire
        coEvery { isTypeActiveUC(type) } returns Result.Success(false)
        coEvery { hasReachedMaxActiveTypesUC() } returns Result.Success(true)
        coEvery { typeRepository.fetchType(type) } returns Result.Success(Unit)
        coEvery { removeOldestTypeUC() } returns Result.Error.Local.CouldNotSave

        // when
        val result = useCaseImpl(type)

        // then
        assertTrue(result is Result.Error.Local.CouldNotSave)

        coVerifyOrder {
            isTypeActiveUC(type)
            hasReachedMaxActiveTypesUC()
            typeRepository.fetchType(type)
            removeOldestTypeUC()
        }
        coVerify(exactly = 0) { typeRepository.updateLastAccessed(any()) }
    }

    // endregion

}