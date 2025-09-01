interface PokemonRepository {

    suspend fun getByName(name: String): Result<Pokemon>

    suspend fun deleteByNames(names: List<String>): Result<Unit>
}