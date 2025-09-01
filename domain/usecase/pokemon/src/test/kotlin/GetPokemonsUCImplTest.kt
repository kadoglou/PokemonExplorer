package domain.usecase.pokemon

import GetPokemonsUC
import GetPokemonsUCImpl
import Pokemon
import PokemonRepository
import PokemonType
import TypeRepository
import Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPokemonsUCImplTest {

    // region Private fields

    private lateinit var typeRepo: TypeRepository
    private lateinit var pokemonRepo: PokemonRepository
    private lateinit var usecaseImpl: GetPokemonsUC

    // endregion

    // region Setup

    @Before
    fun setup() {
        typeRepo = mockk()
        pokemonRepo = mockk()
        usecaseImpl = GetPokemonsUCImpl(typeRepo, pokemonRepo)
    }

    // endregion

    // region Tests

    @Test
    fun `should return a List of Result Pokemons when all fetches succeed`() = runTest {
        // given
        val typeParameter = PokemonType.Fire
        val names = listOf("Charmander", "Charmeleon")

        coEvery {
            typeRepo.getPagedPokemonRefs(
                type = typeParameter,
                query = any(),
                page = any(),
                pageSize = any()
            )
        } returns Result.Success(names)
        coEvery { pokemonRepo.getByName("Charmander") } returns Result.Success(fakePokemon("Charmander"))
        coEvery { pokemonRepo.getByName("Charmeleon") } returns Result.Success(fakePokemon("Charmeleon"))

        // when
        val result = usecaseImpl(type = typeParameter, query = "Char", page = 0, pageSize = 2)

        // then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data

        // order must match the names list
        assertEquals(listOf("Charmander", "Charmeleon"), data.map { it.name })

        coVerify(exactly = 1) {
            typeRepo.getPagedPokemonRefs(typeParameter, "Char", 0, 2)
            pokemonRepo.getByName("Charmander")
            pokemonRepo.getByName("Charmeleon")
        }
        confirmVerified(typeRepo, pokemonRepo)
    }

    @Test
    fun `should return a Result Error when one of the fetches fails`() = runTest {
        // given
        val typeParameter = PokemonType.Fire
        val names = listOf("Charmander", "Charmeleon")

        coEvery {
            typeRepo.getPagedPokemonRefs(
                type = typeParameter,
                query = any(),
                page = any(),
                pageSize = any()
            )
        } returns Result.Success(names)
        coEvery { pokemonRepo.getByName("Charmander") } returns Result.Success(fakePokemon("Charmander"))
        coEvery { pokemonRepo.getByName("Charmeleon") } returns Result.Error.Remote.NotFound

        // when
        val result = usecaseImpl(type = typeParameter, query = "Char", page = 0, pageSize = 2)

        // then
        assertTrue(result is Result.Error.Remote.NotFound)

        coVerify(exactly = 1) {
            typeRepo.getPagedPokemonRefs(typeParameter, "Char", 0, 2)
            pokemonRepo.getByName("Charmander")
            pokemonRepo.getByName("Charmeleon")
        }
        confirmVerified(typeRepo, pokemonRepo)
    }

    @Test
    fun `should forward the Result Error of typeRepo call when typeRepo getPagedPokemonRefs fails, and not call any Pokemon fetch`() =
        runTest {
            // given
            val typeParameter = PokemonType.Fire

            coEvery {
                typeRepo.getPagedPokemonRefs(
                    type = typeParameter,
                    query = any(),
                    page = any(),
                    pageSize = any()
                )
            } returns Result.Error.Remote.Unavailable

            // when
            val result = usecaseImpl(type = typeParameter, query = "Char", page = 0, pageSize = 2)

            // then
            assertTrue(result is Result.Error.Remote.Unavailable)

            coVerify(exactly = 1) {
                typeRepo.getPagedPokemonRefs(typeParameter, "Char", 0, 2)
            }
            coVerify(exactly = 0) { pokemonRepo.getByName(any()) }
            confirmVerified(typeRepo, pokemonRepo)
        }

    // endregion

}


// region Private methods

private fun fakePokemon(name: String) = Pokemon(
    name = name,
    hp = 50,
    attack = 50,
    defense = 50,
    imageUrl = "https://fake.com/$name.png"
)

// endregion
