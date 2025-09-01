package data.dto

import data.response.PokemonRefDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypeDto(
    val name: String,
    @SerialName("pokemon")
    val pokemonRefs: List<PokemonRefDto>
)
