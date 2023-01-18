package desperate.giphytestcase.domain.model

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Images(
    @Embedded
    @SerialName("downsized_small")
    val downsized: Image
)