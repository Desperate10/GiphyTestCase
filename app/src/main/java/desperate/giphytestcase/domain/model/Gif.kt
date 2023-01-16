package desperate.giphytestcase.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Gif(
    val id: String,
    val images: Images
)