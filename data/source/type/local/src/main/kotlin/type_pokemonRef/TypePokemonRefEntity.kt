package type_pokemonRef

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import type.TypeEntity

@Entity(
    tableName = "type_pokemonRef",
    primaryKeys = ["pokemonName", "typeName"],
    foreignKeys = [
        ForeignKey(
            entity = TypeEntity::class,
            parentColumns = ["name"],
            childColumns = ["typeName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("pokemonName"), Index("typeName")]
)
data class TypePokemonRefEntity(
    val pokemonName: String,
    val typeName: String
)