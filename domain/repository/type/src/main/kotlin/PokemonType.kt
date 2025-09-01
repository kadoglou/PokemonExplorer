/**
 * Represents the supported types of Pokemon by the app.
 *
 * The [apiName] is the string name used by the endpoint (PokeApi).
 *
 * @property apiName The string value of PokeApi
 *
 * @function fromApiName Converts a PokeApi type string into the corresponding [PokemonType] enum.
 * Throws [IllegalArgumentException] if the provided value is not a known type.
 */
enum class PokemonType(val apiName: String) {
    Dark("dark"),
    Dragon("dragon"),
    Electric("electric"),
    Fairy("fairy"),
    Fire("fire"),
    Ghost("ghost"),
    Grass("grass"),
    Psychic("psychic"),
    Steel("steel"),
    Water("water");

    companion object {
        fun fromApiName(value: String): PokemonType =
            entries.firstOrNull { it.apiName.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("No enum constant PokemonType for apiName=\"$value\"")
    }
}