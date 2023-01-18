package desperate.giphytestcase.presentation.screens.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import desperate.giphytestcase.data.mapper.mapDomainModelToView
import desperate.giphytestcase.data.remote.connectivity.ConnectivityObserver
import desperate.giphytestcase.domain.repository.GiphyRepository
import desperate.giphytestcase.presentation.model.GifView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: GiphyRepository,
    private val networkConnectivityObserver: ConnectivityObserver,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val status = networkConnectivityObserver.observe().shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L)
    )

    private val _gifs = MutableStateFlow<PagingData<GifView>>(PagingData.empty())
    val gifs: StateFlow<PagingData<GifView>> = _gifs

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    init {
        getTrendingGifs()
    }

    fun getTrendingGifs() {
        viewModelScope.launch(dispatcher + handler) {
            repository.getTrending().cachedIn(viewModelScope).collect { gif ->
                _gifs.value = gif.map { mapDomainModelToView(it) }
            }
        }
    }

    fun searchGifsByQuery(query: String) {
        viewModelScope.launch(dispatcher + handler) {
            repository.search(query = query).cachedIn(viewModelScope).collect { gif ->
                _gifs.value = gif.map { mapDomainModelToView(it) }
            }
        }
    }

    fun deleteGif(gif: GifView) {
        viewModelScope.launch(dispatcher + handler) {
            repository.deleteGif(gif.id)
        }
    }

    fun setSearchQueryText(text: String) {
        _searchQuery.value = text
    }
}