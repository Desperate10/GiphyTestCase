package desperate.giphytestcase.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import desperate.giphytestcase.data.local.entity.model.Images
import desperate.giphytestcase.utils.Constants.GIPHY_IMAGE_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = GIPHY_IMAGE_TABLE)
@Serializable
data class GifDbModel (
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @Embedded
    var url: Images,
    var isDeleted: Boolean = false
)