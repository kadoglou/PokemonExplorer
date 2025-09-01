import data.dto.PokemonDto

// Dto -> Domain
fun PokemonDto.toDomain(): Pokemon =
    Pokemon(
        name = name,
        hp = hp,
        attack = attack,
        defense = defense,
        imageUrl = imageUrl
    )

// Entity -> Domain
fun PokemonEntity.toDomain(): Pokemon =
    Pokemon(
        name = name,
        hp = hp,
        attack = attack,
        defense = defense,
        imageUrl = imageUrl
    )

// Dto -> Entity
fun PokemonDto.toEntity(
    now: Long = System.currentTimeMillis()
): PokemonEntity =
    PokemonEntity(
        name = name,
        hp = hp,
        attack = attack,
        defense = defense,
        imageUrl = imageUrl,
        lastFetchedAt = now
    )