package mapper

import data.dto.PokemonDto
import data.response.PokemonResponse

internal fun PokemonResponse.toDetailsDto(): PokemonDto {
    fun statValue(key: String): Int =
        stats.firstOrNull { it.stat.name.equals(key, ignoreCase = true) }?.baseStat ?: 0

    val hp = statValue("hp")
    val attack = statValue("attack")
    val defense = statValue("defense")

    val image = sprites.other?.officialArtwork?.frontDefault ?: ""

    return PokemonDto(
        name = name,
        hp = hp,
        attack = attack,
        defense = defense,
        imageUrl = image,
    )
}