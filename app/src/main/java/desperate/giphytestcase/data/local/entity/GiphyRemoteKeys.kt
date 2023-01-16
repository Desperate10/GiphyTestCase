package desperate.giphytestcase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import desperate.giphytestcase.utils.Constants.GIPHY_REMOTE_KEYS

@Entity(tableName = GIPHY_REMOTE_KEYS)
data class GiphyRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
