import data.dto.TypeDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


interface TypeRemoteDataSource {
    suspend fun fetch(name: String): Result<TypeDto>
}

internal const val POKEAPI_BASE = "https://pokeapi.co/api/v2"

internal class TypeRemoteDataSourceImpl(
    private val client: HttpClient
) : TypeRemoteDataSource {

    override suspend fun fetch(name: String): Result<TypeDto> = safeCallRemote {
        client.get("$POKEAPI_BASE/type/${name.lowercase()}").body<TypeDto>()
    }
}