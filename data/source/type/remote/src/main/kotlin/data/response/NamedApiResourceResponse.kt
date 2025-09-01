package data.response

import kotlinx.serialization.Serializable

@Serializable
data class NamedApiResourceResponse(
    val name: String,
    val url: String
)