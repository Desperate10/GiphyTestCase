package desperate.giphytestcase.data.repository

import androidx.paging.*
import desperate.giphytestcase.data.local.GiphyDatabase
import desperate.giphytestcase.data.mapper.mapDbModelToDtModel
import desperate.giphytestcase.data.paging.GiphyRemoteMediator
import desperate.giphytestcase.data.paging.SearchPagingSource
import desperate.giphytestcase.data.remote.GiphyApi
import desperate.giphytestcase.domain.model.Gif
import desperate.giphytestcase.domain.repository.GiphyRepository
import desperate.giphytestcase.utils.Constants.ITEMS_PER_PAGE_SEARCH
import desperate.giphytestcase.utils.Constants.ITEMS_PER_PAGE_TRENDING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GiphyRepositoryImpl @Inject constructor(
    private val giphyApi: GiphyApi,
    private val giphyDatabase: GiphyDatabase
) : GiphyRepository {

    override fun getTrending(): Flow<PagingData<Gif>> {
        val pagingSourceFactory = { giphyDatabase.giphyDao().getGifs() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE_TRENDING),
            remoteMediator = GiphyRemoteMediator(
                giphyApi = giphyApi,
                giphyDatabase = giphyDatabase,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { mapDbModelToDtModel(it) }
        }
    }

    override fun search(query: String): Flow<PagingData<Gif>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE_SEARCH),
            pagingSourceFactory = {
                SearchPagingSource(giphyApi = giphyApi, query = query)
            }
        ).flow
    }

    override suspend fun deleteGif(gifId: String) {
        giphyDatabase.giphyDao().hideGif(gifId)
    }
}