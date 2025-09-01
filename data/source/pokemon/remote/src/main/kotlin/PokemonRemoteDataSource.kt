import data.dto.PokemonDto
import data.response.PokemonResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import mapper.toDetailsDto

private val logger = KotlinLogging.logger {}

private const val POKEAPI_BASE = "https://pokeapi.co/api/v2"

interface PokemonRemoteDataSource {

    suspend fun fetch(name: String): Result<PokemonDto>

}

internal class PokemonRemoteDataSourceImpl(
    private val client: HttpClient
) : PokemonRemoteDataSource {

    override suspend fun fetch(name: String): Result<PokemonDto> =
        safeCallRemote {
            client
                .get("$POKEAPI_BASE/pokemon/$name")
                .body<PokemonResponse>()
                .toDetailsDto()
        }

}