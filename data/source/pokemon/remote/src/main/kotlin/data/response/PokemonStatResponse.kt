package data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonStatResponse(
    @SerialName("base_stat") val baseStat: Int,
    val stat: StatInfoResponse
)