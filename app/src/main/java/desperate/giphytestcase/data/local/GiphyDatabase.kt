package desperate.giphytestcase.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import desperate.giphytestcase.data.local.dao.GiphyDao
import desperate.giphytestcase.data.local.dao.GiphyRemoteKeysDao
import desperate.giphytestcase.data.local.entity.GifDbModel
import desperate.giphytestcase.data.local.entity.GiphyRemoteKeys

@Database(entities = [GifDbModel::class, GiphyRemoteKeys::class], version = 1, exportSchema = false)
abstract class GiphyDatabase: RoomDatabase() {

    abstract fun giphyDao(): GiphyDao

    abstract fun remoteKeysDao() : GiphyRemoteKeysDao

}