package desperate.giphytestcase.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import desperate.giphytestcase.data.local.entity.GifDbModel

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(gifs: List<GifDbModel>)

    @Query("SELECT * FROM giphy_table WHERE isDeleted = 0")
    fun getGifs(): PagingSource<Int, GifDbModel>

    @Query("DELETE FROM giphy_table WHERE isDeleted = 0")
    suspend fun deleteAll()
}