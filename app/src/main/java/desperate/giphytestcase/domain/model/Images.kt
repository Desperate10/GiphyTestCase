package desperate.giphytestcase.domain.model

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class Images(
    @Embedded
    val downsized: Image
)