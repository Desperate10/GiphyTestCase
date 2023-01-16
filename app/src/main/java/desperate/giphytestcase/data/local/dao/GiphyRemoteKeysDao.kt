package desperate.giphytestcase.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import desperate.giphytestcase.data.local.entity.GiphyRemoteKeys

@Dao
interface GiphyRemoteKeysDao {

    @Query("SELECT * FROM giphy_keys WHERE id =:id")
    suspend fun getRemoteKeys(id: String) : GiphyRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(remoteKeys: List<GiphyRemoteKeys>)

    @Query("DELETE FROM giphy_keys")
    suspend fun deleteRemoteKeys()
}