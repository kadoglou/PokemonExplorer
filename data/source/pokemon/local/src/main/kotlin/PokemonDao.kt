import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: PokemonEntity)

    @Query("SELECT * FROM pokemon WHERE name = :name")
    suspend fun get(name: String): PokemonEntity?

    @Query("DELETE FROM pokemon WHERE name IN (:names)")
    suspend fun deleteMany(names: List<String>)

}