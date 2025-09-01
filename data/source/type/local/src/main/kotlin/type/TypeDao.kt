package type

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeDao {

    /** Insert or update a type (keeps last access timestamp) */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(type: TypeEntity)

    @Query("SELECT * FROM type ORDER BY lastAccessedAt DESC")
    suspend fun getCachedTypes(): List<TypeEntity>

    /** Update lastAccessedAt timestamp */
    @Query("UPDATE type SET lastAccessedAt = :timestamp WHERE name = :typeName")
    suspend fun updateLastAccessed(typeName: String, timestamp: Long)

    @Query("DELETE FROM type WHERE name = :typeName")
    suspend fun deleteByName(typeName: String)

    /** Observe the currently active type (most recently accessed) */
    @Query("SELECT * FROM type ORDER BY lastAccessedAt DESC LIMIT 1")
    fun observeActiveType(): Flow<TypeEntity?>
}