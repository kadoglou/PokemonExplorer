package data.response

import kotlinx.serialization.Serializable

@Serializable
data class PokemonRefDto(
    val pokemon: NamedApiResourceResponse
)