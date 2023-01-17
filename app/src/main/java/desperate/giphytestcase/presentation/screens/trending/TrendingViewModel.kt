package desperate.giphytestcase.presentation.screens.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import desperate.giphytestcase.data.mapper.mapDomainModelToView
import desperate.giphytestcase.domain.repository.GiphyRepository
import desperate.giphytestcase.presentation.model.GifView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    private val _gifs = MutableStateFlow<PagingData<GifView>>(PagingData.empty())
    val gifs : StateFlow<PagingData<GifView>> = _gifs

    init {
        getTrendingGifs()
    }

    fun getTrendingGifs() {
        viewModelScope.launch {
            repository.getTrending().cachedIn(viewModelScope).collect { gif->
                _gifs.value = gif.map { mapDomainModelToView(it) }
            }
        }
    }

    fun searchGifsByQuery(query: String) {
        viewModelScope.launch {
            repository.search(query = query).cachedIn(viewModelScope).collect { gif->
                _gifs.value = gif.map { mapDomainModelToView(it) }
            }
        }
    }
}