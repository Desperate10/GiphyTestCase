package desperate.giphytestcase.domain.repository

import androidx.paging.PagingData
import desperate.giphytestcase.domain.model.Gif
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {

    fun getTrending(): Flow<PagingData<Gif>>

    fun search(query: String): Flow<PagingData<Gif>>

    suspend fun deleteGif(gifId: String)

}