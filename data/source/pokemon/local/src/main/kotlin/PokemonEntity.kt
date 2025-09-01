import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val name: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val imageUrl: String,
    val lastFetchedAt: Long
)