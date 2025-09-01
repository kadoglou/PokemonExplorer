package type_pokemonRef

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TypePokemonRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(refs: List<TypePokemonRefEntity>)

    /**
     * Search pokemon names by type with an optional case-insensitive prefix and pagination.
     * If [prefix] is empty, it returns all names for the type (still paged).
     */
    @Query(
        """
        SELECT pokemonName
        FROM type_pokemonRef
        WHERE typeName = :typeName
          AND (:prefix = '' OR pokemonName LIKE :prefix || '%' COLLATE NOCASE)
        LIMIT :limit OFFSET :offset
        """
    )
    suspend fun getPagedNamesByType(
        typeName: String,
        prefix: String,
        limit: Int,
        offset: Int
    ): List<String>

    /**
     * Count total matches for the same search; useful if you want to know when you've reached the end.
     */
    @Query(
        """
        SELECT COUNT(*)
        FROM type_pokemonRef
        WHERE typeName = :typeName
          AND (:prefix = '' OR pokemonName LIKE :prefix || '%' COLLATE NOCASE)
        """
    )
    suspend fun countNamesByTypePrefix(
        typeName: String,
        prefix: String
    ): Int

    @Query(
        """
        SELECT DISTINCT r1.pokemonName
        FROM type_pokemonRef r1
        WHERE r1.typeName = :typeName
          AND 1 = (
            SELECT COUNT(*)
            FROM type_pokemonRef r2
            WHERE r2.pokemonName = r1.pokemonName
          )
        """
    )
    suspend fun getPokemonsOnlyOfType(typeName: String): List<String>
}