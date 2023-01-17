package desperate.giphytestcase.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import desperate.giphytestcase.data.local.GiphyDatabase
import desperate.giphytestcase.data.local.entity.GifDbModel
import desperate.giphytestcase.data.local.entity.GiphyRemoteKeys
import desperate.giphytestcase.data.mapper.mapDtModelToDbModel
import desperate.giphytestcase.data.remote.GiphyApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GiphyRemoteMediator @Inject constructor(
    private val giphyApi: GiphyApi,
    private val giphyDatabase: GiphyDatabase
) : RemoteMediator<Int, GifDbModel>() {

    private val giphyDao = giphyDatabase.giphyDao()
    private val giphyRemoteKeysDao = giphyDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifDbModel>
    ): MediatorResult {
        return try {
            val currentPage = when(loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?:return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = giphyApi.getTrending(
                offset = when(loadType) {
                    LoadType.REFRESH -> 0
                    else -> currentPage * 20
                }
            ).data

            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage-1
            val nextPage = if (endOfPaginationReached) null else currentPage+1

            giphyDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    giphyDao.deleteAll()
                    giphyRemoteKeysDao.deleteRemoteKeys()
                }
                val keys = response.map { gif ->
                    GiphyRemoteKeys(
                        id = gif.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                giphyRemoteKeysDao.addRemoteKeys(remoteKeys = keys)
                giphyDao.insertAll(gifs = response.map { mapDtModelToDbModel(it) })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GifDbModel>
    ): GiphyRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                giphyRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, GifDbModel>
    ): GiphyRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty()}?.data?.firstOrNull()?.let {
                gif ->
            giphyRemoteKeysDao.getRemoteKeys(gif.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, GifDbModel>
    ): GiphyRemoteKeys? {
        return state.pages.lastOrNull {it.data.isNotEmpty()}?.data?.lastOrNull()
            ?.let { gif ->
                giphyRemoteKeysDao.getRemoteKeys(id = gif.id)
            }
    }
}