import AppConstantSettings.MAX_CACHED_POKEMON_ENTRIES
import androidx.collection.LruCache

object PokemonCacheMemory {


    private val cache = object : LruCache<String, Pokemon>(MAX_CACHED_POKEMON_ENTRIES) {
        override fun sizeOf(key: String, value: Pokemon): Int = 1
    }

    fun savePokemon(pokemon: Pokemon) {
        cache.put(pokemon.name, pokemon)
    }

    fun getPokemon(name: String): Pokemon? = cache[name]

    fun remove(name: String) {
        cache.remove(name)
    }

}