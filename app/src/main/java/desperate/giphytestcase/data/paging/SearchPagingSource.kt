package desperate.giphytestcase.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import desperate.giphytestcase.data.remote.GiphyApi
import desperate.giphytestcase.domain.model.Gif

class SearchPagingSource (
    private val giphyApi: GiphyApi,
    private val query: String
) : PagingSource<Int, Gif>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
        val currentPage = params.key ?: 1
        return try {
            val response = giphyApi.searchGifs(query = query)
            val endOfPaginationReached = response.data.isEmpty()
            if (response.data.isNotEmpty()) {
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Gif>): Int? {
        return state.anchorPosition
    }

}