import data.dto.TypeDto
import type_pokemonRef.TypePokemonRefEntity
import type.TypeEntity

// Entity + Pokémon names -> Domain
fun TypeEntity.toDomain(pokemonNames: List<String>): Type =
    Type(
        pokemonType = PokemonType.fromApiName(name),
        pokemonNames = pokemonNames
    )

// Dto -> Entity
fun TypeDto.toEntity(
    now: Long = System.currentTimeMillis()
): TypeEntity =
    TypeEntity(
        name = name,
        lastAccessedAt = now
    )

// Dto -> Pokémon refs
fun TypeDto.toRefs(): List<TypePokemonRefEntity> =
    pokemonRefs.map { ref ->
        TypePokemonRefEntity(
            pokemonName = ref.pokemon.name,
            typeName = name
        )
    }