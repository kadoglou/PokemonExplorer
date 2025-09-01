package data.response

import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonResponse(
    val name: String,
    val stats: List<PokemonStatResponse>,
    val sprites: SpritesResponse,
)