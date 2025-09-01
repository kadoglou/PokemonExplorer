package data.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SpritesResponse(
    val other: OtherSpritesResponse? = null
)