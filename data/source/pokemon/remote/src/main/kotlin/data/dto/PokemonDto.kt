package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val name: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val imageUrl: String
)